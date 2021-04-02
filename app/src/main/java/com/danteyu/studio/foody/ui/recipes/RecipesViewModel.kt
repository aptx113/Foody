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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteyu.studio.foody.API_KEY
import com.danteyu.studio.foody.QUERY_ADD_RECIPE_INFORMATION
import com.danteyu.studio.foody.QUERY_API_KEY
import com.danteyu.studio.foody.QUERY_DIET
import com.danteyu.studio.foody.QUERY_FILL_INGREDIENTS
import com.danteyu.studio.foody.QUERY_NUM
import com.danteyu.studio.foody.QUERY_TYPE
import com.danteyu.studio.foody.data.repository.FoodyRepository
import com.danteyu.studio.foody.model.FoodRecipe
import com.danteyu.studio.foody.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by George Yu on 2021/4/1.
 */
@HiltViewModel
class RecipesViewModel @Inject constructor(private val repository: FoodyRepository) : ViewModel() {

    private val _recipesFlow = MutableStateFlow<NetworkResult<FoodRecipe>>(NetworkResult.Loading())
    val recipesFlow: StateFlow<NetworkResult<FoodRecipe>> = _recipesFlow

    fun getRecipes(queries: Map<String, String>) =
        repository.getRecipesFlow(queries)
            .onEach { _recipesFlow.value = it }
            .launchIn(viewModelScope)

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUM] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}
