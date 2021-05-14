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

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ListAdapter
import com.danteyu.studio.foody.R
import com.danteyu.studio.foody.ext.applyStatusBarColor
import com.danteyu.studio.foody.ext.showSnackBar
import com.danteyu.studio.foody.model.FoodRecipe
import com.danteyu.studio.foody.ui.common.SingleFieldDiffUtil

/**
 * Created by George Yu in Apr. 2021.
 */
class FavoriteRecipesAdapter(
    private val requiredActivity: FragmentActivity,
    private val viewModel: FavoriteRecipesViewModel,
    private val onClick: (FoodRecipe) -> Unit
) :
    ListAdapter<FoodRecipe, FavoriteRecipesViewHolder>(SingleFieldDiffUtil<FoodRecipe> { it.id }),
    ActionMode.Callback {

    private var multiSelection = false
    private lateinit var actionMode: ActionMode
    private lateinit var rootView: View

    private val selectedRecipes = arrayListOf<FoodRecipe>()
    private val viewHolders = arrayListOf<FavoriteRecipesViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipesViewHolder =
        FavoriteRecipesViewHolder.create(parent)

    override fun onBindViewHolder(holder: FavoriteRecipesViewHolder, position: Int) {
        viewHolders.add(holder)
        rootView = holder.itemView.rootView
        val favoriteRecipe = getItem(position)
        holder.bind(favoriteRecipe)
        holder.itemView.setOnClickListener {
            if (multiSelection) applySelection(holder, favoriteRecipe)
            else onClick(favoriteRecipe)
        }
        holder.itemView.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requiredActivity.startActionMode(this)
                applySelection(holder, favoriteRecipe)
                true
            } else {
                applySelection(holder, favoriteRecipe)
                true
            }
        }
    }

    private fun applySelection(holder: FavoriteRecipesViewHolder, currentRecipe: FoodRecipe) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
        }
        applyActionModeTitle()
    }

    private fun changeRecipeStyle(
        holder: FavoriteRecipesViewHolder,
        @ColorRes backgroundColor: Int,
        @ColorRes strokeColor: Int
    ) {
        holder.viewDataBinding.recipeRowLayout.setBackgroundColor(
            ContextCompat.getColor(
                requiredActivity,
                backgroundColor
            )
        )
        holder.viewDataBinding.itemFavoriteCard.strokeColor =
            ContextCompat.getColor(requiredActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> actionMode.finish()
            else -> actionMode.title = requiredActivity.resources.getQuantityString(
                R.plurals.favorite_recipes,
                selectedRecipes.size,
                selectedRecipes.size
            )
        }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        actionMode = mode ?: return false
        requiredActivity.applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_favorite_recipe) {
            selectedRecipes.forEach { viewModel.deleteFavoriteRecipe(it) }
            requiredActivity.showSnackBar(
                rootView,
                requiredActivity.resources.getQuantityString(
                    R.plurals.deleted_recipes,
                    selectedRecipes.size,
                    selectedRecipes.size
                ),
                requiredActivity.getString(R.string.ok)
            )
            multiSelection = false
            selectedRecipes.clear()
            actionMode.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        viewHolders.forEach { holder ->
            changeRecipeStyle(
                holder,
                R.color.cardBackgroundColor,
                R.color.strokeColor
            )
        }
        multiSelection = false
        selectedRecipes.clear()
        requiredActivity.applyStatusBarColor(R.color.statusBarColor)
    }

    fun clearContextualActionMode() {
        if (this::actionMode.isInitialized) actionMode.finish()
    }
}
