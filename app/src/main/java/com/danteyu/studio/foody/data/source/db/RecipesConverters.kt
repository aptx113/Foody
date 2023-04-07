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

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.danteyu.studio.foody.model.ExtendedIngredient
import com.danteyu.studio.foody.model.FoodRecipe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Created by George Yu on 2021/4/4.
 */
@ProvidedTypeConverter
class RecipesConverters @Inject constructor() {

    @TypeConverter
    fun convertFoodRecipesToJson(foodRecipes: List<FoodRecipe>?): String? {
        return foodRecipes?.let {
            Json.encodeToString(it)
        }
    }

    @TypeConverter
    fun convertJsonToFoodRecipes(json: String?): List<FoodRecipe>? {
        return json?.let {
            Json.decodeFromString<List<FoodRecipe>>(it)
        }
    }

    @TypeConverter
    fun convertIngredientsToJson(ingredients: List<ExtendedIngredient>?): String? {
        return ingredients?.let {
            Json.encodeToString(it)
        }
    }

    @TypeConverter
    fun covertJsonToIngredients(json: String?): List<ExtendedIngredient>? {
        return json?.let {
            Json.decodeFromString<List<ExtendedIngredient>>(it)
        }
    }
}
