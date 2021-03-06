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
package com.danteyu.studio.foody.data.repository

import com.danteyu.studio.foody.data.source.api.FoodyApiService
import com.danteyu.studio.foody.data.source.db.FoodyDao
import com.danteyu.studio.foody.model.FoodRecipe
import com.danteyu.studio.foody.utils.networkBoundResource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by George Yu on 2021/3/29.
 */
class FoodyRepository @Inject constructor(
    private val foodyApiService: FoodyApiService,
    private val foodyDao: FoodyDao
) :
    Repository {

    fun getRecipesFlow(queries: Map<String, String>) =
        flow {
            emit(
                networkBoundResource(
                    apiCall = { foodyApiService.getRecipes(queries) },
                    saveApiCall = { foodyDao.insertRecipes(it) }
                )
            )
        }

    fun loadRecipesFlow() = foodyDao.loadRecipesFlow()

    fun getSearchRecipesFlow(searchQueries: Map<String, String>) = flow {
        emit(
            networkBoundResource(
                apiCall = { foodyApiService.getSearchRecipes(searchQueries) },
                saveApiCall = {}
            )
        )
    }

    fun getFoodJokeFlow(apiKey: String) = flow {
        emit(
            networkBoundResource(
                apiCall = { foodyApiService.getFoodJoke(apiKey) },
                saveApiCall = { foodyDao.insertFoodJoke(it) }
            )
        )
    }

    fun loadFavoriteRecipesFlow() = foodyDao.loadFavoriteRecipesFlow()

    suspend fun insertFavoriteRecipe(foodRecipe: FoodRecipe) =
        foodyDao.insertFavoriteRecipe(foodRecipe)

    suspend fun deleteFavoriteRecipe(foodRecipe: FoodRecipe) =
        foodyDao.deleteFavoriteRecipe(foodRecipe)

    suspend fun deleteAllFavoriteRecipes() = foodyDao.deleteAllFavoriteRecipes()

    fun loadFoodJokeFlow() = foodyDao.loadFoodJoke()
}
