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
package com.danteyu.studio.foody.ui.favoriteRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.danteyu.studio.foody.R
import com.danteyu.studio.foody.databinding.FragmentFavoriteRecipesBinding
import com.danteyu.studio.foody.ext.observeInLifecycle
import com.danteyu.studio.foody.ext.showSnackBar
import com.danteyu.studio.foody.ext.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentFavoriteRecipesBinding
    private val viewModel by viewModels<FavoriteRecipesViewModel>()
    private val adapter by lazy {
        FavoriteRecipesAdapter(requireActivity(), viewModel) {
            viewModel.onDetailsNavigated(
                it
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewDataBinding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        viewDataBinding.recyclerView.adapter = adapter
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewDataBinding.viewModel = viewModel
        provideMenu()

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.favoriteRecipes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.navigateToDetailsFlow
            .onEach {
                findNavController().navigate(
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                        it
                    )
                )
            }
            .observeInLifecycle(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.clearContextualActionMode()
    }

    private fun provideMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.favorite_recipes_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.deleteAll_favorite) {

                    if (viewModel.favoriteRecipes.value?.size == 0) {
                        showToast(getString(R.string.nothing_left_to_remove))
                    } else {
                        viewModel.deleteAllFavoriteRecipes()
                        showSnackBar(
                            viewDataBinding.root,
                            getString(R.string.all_recipes_removed),
                            getString(R.string.ok)
                        )
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
