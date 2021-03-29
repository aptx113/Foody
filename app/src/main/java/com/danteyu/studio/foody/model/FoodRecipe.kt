package com.danteyu.studio.foody.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodRecipe(
    @Json(name = "results")
    val results: List<Result>
)
