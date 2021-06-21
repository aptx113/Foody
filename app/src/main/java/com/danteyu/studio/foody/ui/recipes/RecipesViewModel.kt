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
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.danteyu.studio.foody.API_KEY
import com.danteyu.studio.foody.DEFAULT_RECIPES_NUM
import com.danteyu.studio.foody.QUERY_ADD_RECIPE_INFORMATION
import com.danteyu.studio.foody.QUERY_API_KEY
import com.danteyu.studio.foody.QUERY_DIET
import com.danteyu.studio.foody.QUERY_FILL_INGREDIENTS
import com.danteyu.studio.foody.QUERY_NUM
import com.danteyu.studio.foody.QUERY_SEARCH
import com.danteyu.studio.foody.QUERY_TYPE
import com.danteyu.studio.foody.data.repository.FoodyRepository
import com.danteyu.studio.foody.data.repository.MealAndDietType
import com.danteyu.studio.foody.data.repository.UserPreferencesRepository
import com.danteyu.studio.foody.model.FoodRecipe
import com.danteyu.studio.foody.model.FoodRecipesResponse
import com.danteyu.studio.foody.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by George Yu on 2021/4/1.
 */
@SuppressWarnings("TooManyFunctions")
@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val foodyRepository: FoodyRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private lateinit var mealAndDiet: MealAndDietType

    val recipes = foodyRepository.loadRecipesFlow().asLiveData()

    private val _recipesFlow =
        MutableStateFlow<NetworkResult<FoodRecipesResponse>>(NetworkResult.Loading())
    val recipesFlow: StateFlow<NetworkResult<FoodRecipesResponse>> = _recipesFlow

    private val _searchRecipesFlow =
        MutableStateFlow<NetworkResult<FoodRecipesResponse>>(NetworkResult.Loading())
    val searchRecipesFlow: StateFlow<NetworkResult<FoodRecipesResponse>> = _searchRecipesFlow

    private val navigateToRecipesBottomSheetChannel = Channel<Boolean>(Channel.CONFLATED)
    val navigateToRecipesBottomSheetFlow = navigateToRecipesBottomSheetChannel.receiveAsFlow()

    private val navigateToDetailsChannel = Channel<FoodRecipe>(Channel.CONFLATED)
    val navigateToDetailsFlow = navigateToDetailsChannel.receiveAsFlow()

    private val _networkStatusFlow = MutableStateFlow(false)
    val networkStatusFlow: StateFlow<Boolean> = _networkStatusFlow

    private val _backOnline = MutableStateFlow(false)
    val backOnline: StateFlow<Boolean> = _backOnline

    private val applySelectedChipsChannel = Channel<Boolean>(Channel.CONFLATED)
    val applySelectedChipsFlow = applySelectedChipsChannel.receiveAsFlow()

    val mealAndDietTypeFlow = userPreferencesRepository.mealAndDietFlow

    val backOnlineFlow = userPreferencesRepository.backOnlineFlow

    fun saveMealAndDietType() =
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.saveMealAndDietType(
                mealAndDiet.selectedMealType,
                mealAndDiet.selectedMealTypeId,
                mealAndDiet.selectedDietType,
                mealAndDiet.selectedDietTypeId
            )
        }

    fun saveMealAndDietTypeTemp(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        mealAndDiet = MealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
    }

    fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) { userPreferencesRepository.saveBackOnline(backOnline) }

    fun getRecipes(queries: Map<String, String>) =
        viewModelScope.launch {
            _recipesFlow.value = NetworkResult.Loading()
            foodyRepository.getRecipesFlow(queries)
                .collect { _recipesFlow.value = it }
        }

    fun getSearchRecipes(searchQueries: Map<String, String>) =
        viewModelScope.launch {
            foodyRepository.getSearchRecipesFlow(searchQueries)
                .collect { _searchRecipesFlow.value = it }
        }

    fun applyQueries(mealType: String, dietType: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUM] = DEFAULT_RECIPES_NUM
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun applySearchQueries(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUM] = DEFAULT_RECIPES_NUM
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun onRecipesBottomSheetNavigated() =
        viewModelScope.launch { navigateToRecipesBottomSheetChannel.send(true) }

    fun onDetailsNavigated(foodRecipe: FoodRecipe) =
        viewModelScope.launch { navigateToDetailsChannel.send(foodRecipe) }

    fun onNetworkStatusChecked(hasNetwork: Boolean) {
        _networkStatusFlow.value = hasNetwork
    }

    fun onOnlineBacked(backOnline: Boolean) {
        _backOnline.value = backOnline
    }

    fun onSelectedChipsApplied() = viewModelScope.launch { applySelectedChipsChannel.send(true) }
}
