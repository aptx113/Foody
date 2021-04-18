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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteyu.studio.foody.data.repository.FoodyRepository
import com.danteyu.studio.foody.model.FoodRecipe
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Created by George Yu in Apr. 2021.
 */
class DetailsViewModel @AssistedInject constructor(
    private val repository: FoodyRepository,
    @Assisted private val argument: FoodRecipe
) :
    ViewModel() {

    val foodRecipe = argument

    val isInMyFavoriteFlow = repository.loadFavoriteRecipesFlow().map {
        var contained = false
        it.forEach { favoriteRecipe ->
            if (favoriteRecipe.id == argument.id) contained = true
        }
        contained
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun insertFavoriteRecipe(foodRecipe: FoodRecipe) =
        viewModelScope.launch(Dispatchers.IO) { repository.insertFavoriteRecipe(foodRecipe) }

    fun deleteFavoriteRecipe(foodRecipe: FoodRecipe) =
        viewModelScope.launch(Dispatchers.IO) { repository.deleteFavoriteRecipe(foodRecipe) }
}
