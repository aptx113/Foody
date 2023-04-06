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
package com.danteyu.studio.foody.ui.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.danteyu.studio.foody.R
import com.danteyu.studio.foody.RECIPE_RESULT_KEY
import com.danteyu.studio.foody.databinding.ActivityDetailsBinding
import com.danteyu.studio.foody.ext.observeInLifecycle
import com.danteyu.studio.foody.ext.showSnackBar
import com.danteyu.studio.foody.model.FoodRecipe
import com.danteyu.studio.foody.ui.details.ingredients.IngredientsFragment
import com.danteyu.studio.foody.ui.details.instructions.InstructionsFragment
import com.danteyu.studio.foody.ui.details.overview.OverviewFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>().apply {
            add(OverviewFragment())
            add(IngredientsFragment())
            add(InstructionsFragment())
        }

        val titles = ArrayList<String>().apply {
            add(getString(R.string.overview))
            add(getString(R.string.ingredients))
            add(getString(R.string.instructions))
        }

        val resultBundle = Bundle().apply {
            putParcelable(RECIPE_RESULT_KEY, args.foodRecipe)
        }

        binding.viewPager2.adapter =
            DetailsPagerAdapter(resultBundle, fragments, this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()

        args.toSavedStateHandle()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites)
        checkSavedRecipe(menuItem)
        return true
    }

    private fun checkSavedRecipe(menuItem: MenuItem?) {
        viewModel.isInMyFavoriteFlow
            .onEach {
                if (it) changeItemColor(menuItem, R.color.yellow)
                else changeItemColor(menuItem, R.color.white)
            }
            .observeInLifecycle(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == android.R.id.home -> finish()
            item.itemId == R.id.save_to_favorites && !viewModel.isInMyFavoriteFlow.value -> saveToFavorites(
                args.foodRecipe
            )
            item.itemId == R.id.save_to_favorites && viewModel.isInMyFavoriteFlow.value -> removeFromFavorites(
                args.foodRecipe
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(foodRecipe: FoodRecipe) {
        viewModel.insertFavoriteRecipe(foodRecipe)
        showSnackBar(
            binding.detailsLayout,
            getString(R.string.recipe_saved),
            getString(R.string.ok)
        )
    }

    private fun removeFromFavorites(foodRecipe: FoodRecipe) {
        viewModel.deleteFavoriteRecipe(foodRecipe)
        showSnackBar(
            binding.detailsLayout,
            getString(R.string.removed_from_favorites),
            getString(R.string.ok)
        )
    }

    private fun changeItemColor(item: MenuItem?, @ColorRes color: Int) {
        item?.icon?.setTint(ContextCompat.getColor(this, color))
    }
}
