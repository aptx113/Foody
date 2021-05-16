/*
 * Copyright 2021 Dante Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.danteyu.studio.foody.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danteyu.studio.foody.R
import com.danteyu.studio.foody.databinding.FragmentRecipesBinding
import com.danteyu.studio.foody.ext.observeInLifecycle
import com.danteyu.studio.foody.ext.observeOnce
import com.danteyu.studio.foody.ext.showToast
import com.danteyu.studio.foody.utils.NetworkListener
import com.danteyu.studio.foody.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentRecipesBinding
    private lateinit var networkListener: NetworkListener
    private val viewModel by activityViewModels<RecipesViewModel>()
    private val adapter by lazy { RecipesAdapter { viewModel.onDetailsNavigated(it) } }
    private val args by navArgs<RecipesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentRecipesBinding.inflate(layoutInflater, container, false)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewDataBinding.viewModel = viewModel

        setHasOptionsMenu(true)
        setupRecyclerView()
        networkListener = NetworkListener()

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToRecipesBottomSheetFlow
            .onEach {
                if (viewModel.networkStatusFlow.value)
                    findNavController().navigate(R.id.recipesBottomSheetFragment)
                else showNetworkStatus(
                    viewModel.networkStatusFlow.value,
                    viewModel.backOnline.value
                )
            }
            .observeInLifecycle(viewLifecycleOwner)

        viewModel.navigateToDetailsFlow
            .onEach {
                findNavController().navigate(
                    RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(it)
                )
            }.observeInLifecycle(viewLifecycleOwner)

        viewModel.backOnlineFlow
            .onEach { viewModel.onOnlineBacked(it) }
            .observeInLifecycle(viewLifecycleOwner)

        networkListener.checkNetworkAvailability(requireContext())
            .onEach { hasNetwork ->
                Timber.d(hasNetwork.toString())
                viewModel.onNetworkStatusChecked(hasNetwork)
                showNetworkStatus(viewModel.networkStatusFlow.value, viewModel.backOnline.value)
                loadDataFromCache(
                    action = { hideShimmerEffect() },
                    request = { requestApiData(args.mealType, args.dietType) }
                )
            }.observeInLifecycle(viewLifecycleOwner)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchApiData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
    }

    private fun setupRecyclerView() {
        viewDataBinding.recipesRecycler.adapter = adapter
        showShimmerEffect()
    }

    private fun loadDataFromCache(action: () -> Unit = {}, request: () -> Unit = {}) {
        viewModel.recipes.observeOnce(viewLifecycleOwner) {
            if (it.isNotEmpty() && !args.backFromBottomSheet) {
                Timber.d("readDatabase called!!")
                adapter.submitList(it[0].foodRecipes)
                action()
            } else {
                request()
            }
        }
    }

    private fun requestApiData(mealType: String, dietType: String) {
        Timber.d("requestApiData called!!")
        viewModel.getRecipes(viewModel.applyQueries(mealType, dietType))
        viewModel.recipesFlow.onEach { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { adapter.submitList(it.foodRecipes) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    showToast(response.message.toString())
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }.observeInLifecycle(viewLifecycleOwner)
    }

    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        viewModel.getSearchRecipes(viewModel.applySearchQueries(searchQuery))
        viewModel.searchRecipesFlow.onEach { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { adapter.submitList(it.foodRecipes) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    showToast(response.message.toString())
                }
                is NetworkResult.Loading -> showShimmerEffect()
            }
        }.observeInLifecycle(viewLifecycleOwner)
    }

    private fun showShimmerEffect() = viewDataBinding.recipesRecycler.showShimmer()
    private fun hideShimmerEffect() = viewDataBinding.recipesRecycler.hideShimmer()

    private fun showNetworkStatus(hasNetwork: Boolean, backOnline: Boolean) {
        if (!hasNetwork) {
            showToast(getString(R.string.no_internet_connection))
            viewModel.saveBackOnline(true)
        } else {
            if (backOnline) {
                showToast(getString(R.string.back_online))
                viewModel.saveBackOnline(false)
            }
        }
    }
}
