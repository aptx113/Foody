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
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danteyu.studio.foody.R
import com.danteyu.studio.foody.databinding.FragmentRecipesBinding
import com.danteyu.studio.foody.ext.observeInLifecycle
import com.danteyu.studio.foody.ext.observeOnce
import com.danteyu.studio.foody.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentRecipesBinding
    private val viewModel by activityViewModels<RecipesViewModel>()
    private val adapter by lazy { RecipesAdapter() }
    private val args by navArgs<RecipesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentRecipesBinding.inflate(layoutInflater, container, false)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewDataBinding.viewModel = viewModel
        setupRecyclerView()
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadDataFromCache(action = { hideShimmerEffect() }, request = { requestApiData() })
        viewModel.navigateToRecipesBottomSheetFlow
            .onEach { if (it) findNavController().navigate(R.id.recipesBottomSheetFragment) }
            .observeInLifecycle(viewLifecycleOwner)
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

    private fun requestApiData() {
        Timber.d("requestApiData called!!")
        viewModel.getRecipes(viewModel.applyQueries())
        viewModel.recipesFlow.onEach { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { adapter.submitList(it.foodRecipes) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }.observeInLifecycle(viewLifecycleOwner)
    }

    private fun showShimmerEffect() = viewDataBinding.recipesRecycler.showShimmer()
    private fun hideShimmerEffect() = viewDataBinding.recipesRecycler.hideShimmer()
}
