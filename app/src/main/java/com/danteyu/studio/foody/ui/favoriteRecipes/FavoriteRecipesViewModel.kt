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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteyu.studio.foody.data.repository.FoodyRepository
import com.danteyu.studio.foody.model.FoodRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by George Yu in Apr. 2021.
 */
@HiltViewModel
class FavoriteRecipesViewModel @Inject constructor(private val repository: FoodyRepository) :
    ViewModel() {

    val favoriteRecipesFlow = repository.loadFavoriteRecipesFlow()

    private fun deleteFavoriteRecipe(foodRecipe: FoodRecipe) =
        viewModelScope.launch(Dispatchers.IO) { repository.deleteFavoriteRecipe(foodRecipe) }

    private fun deleteAllFavoriteRecipes() =
        viewModelScope.launch(Dispatchers.IO) { repository.deleteAllFavoriteRecipes() }
}
