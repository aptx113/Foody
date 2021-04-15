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
package com.danteyu.studio.foody.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danteyu.studio.foody.RECIPES_TABLE
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = RECIPES_TABLE)
@JsonClass(generateAdapter = true)
data class FoodRecipe(
    @Json(name = "aggregateLikes")
    val aggregateLikes: Int,
    @Json(name = "cheap")
    val cheap: Boolean,
    @Json(name = "dairyFree")
    val dairyFree: Boolean,
    @Json(name = "extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>,
    @Json(name = "glutenFree")
    val glutenFree: Boolean,
    @PrimaryKey
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val image: String,
    @Json(name = "readyInMinutes")
    val readyInMinutes: Int,
    @Json(name = "sourceName")
    val sourceName: String?,
    @Json(name = "sourceUrl")
    val sourceUrl: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "vegan")
    val vegan: Boolean,
    @Json(name = "vegetarian")
    val vegetarian: Boolean,
    @Json(name = "veryHealthy")
    val veryHealthy: Boolean
) : Parcelable
