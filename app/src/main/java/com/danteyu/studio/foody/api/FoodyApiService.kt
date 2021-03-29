package com.danteyu.studio.foody.api

import com.danteyu.studio.foody.model.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by George Yu on 2021/3/29.
 */
interface FoodyApiService {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries: Map<String, String>): Response<FoodRecipe>
}
