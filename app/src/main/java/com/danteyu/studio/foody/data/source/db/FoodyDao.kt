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
package com.danteyu.studio.foody.data.source.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danteyu.studio.foody.model.FoodJoke
import com.danteyu.studio.foody.model.FoodRecipe
import com.danteyu.studio.foody.model.FoodRecipesResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by George Yu on 2021/4/2.
 */
@Dao
interface FoodyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(foodRecipesResponse: FoodRecipesResponse)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun loadRecipesFlow(): Flow<List<FoodRecipesResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(foodRecipe: FoodRecipe)

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun loadFavoriteRecipesFlow(): Flow<List<FoodRecipe>>

    @Delete
    suspend fun deleteFavoriteRecipe(foodRecipe: FoodRecipe)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJoke: FoodJoke)

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun loadFoodJoke(): Flow<List<FoodJoke>>
}
