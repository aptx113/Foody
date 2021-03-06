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
package com.danteyu.studio.foody.utils

import com.danteyu.studio.foody.PAYMENT_REQUIRED
import com.danteyu.studio.foody.R
import com.danteyu.studio.foody.TIME_OUT
import com.danteyu.studio.foody.model.FoodRecipesResponse
import com.danteyu.studio.foody.utils.Util.getString
import com.danteyu.studio.foody.utils.Util.hasInternetConnection
import retrofit2.Response
import java.lang.Exception

/**
 * Created by George Yu on 2021/3/31.
 */
@SuppressWarnings("TooGenericExceptionCaught", "NestedBlockDepth")
suspend inline fun <T> networkBoundResource(
    crossinline apiCall: suspend () -> Response<T>,
    crossinline saveApiCall: suspend (T) -> Unit
): NetworkResult<T> = if (hasInternetConnection()) {
    try {
        handleApiResponse(apiCall.invoke()).let {
            when {
                it.data is FoodRecipesResponse &&
                    (it.data as FoodRecipesResponse).foodRecipes.isNullOrEmpty() -> {
                }
                it.data != null -> saveApiCall(it.data)
            }
            it
        }
    } catch (e: Exception) {
        NetworkResult.Error("Error with $e")
    }
} else {
    NetworkResult.Error(getString(R.string.no_internet_connection))
}

fun <T> handleApiResponse(response: Response<T>): NetworkResult<T> {
    return with(response) {
        when {
            message()
                .contains(TIME_OUT) -> NetworkResult.Error(getString(R.string.timeout))
            code() == PAYMENT_REQUIRED -> NetworkResult.Error(getString(R.string.api_key_limited))
            isSuccessful -> {
                when {
                    body() is FoodRecipesResponse &&
                        (body() as FoodRecipesResponse).foodRecipes.isNullOrEmpty() ->
                        NetworkResult.Error(getString(R.string.resource_not_found))
                    body() != null -> NetworkResult.Success(body()!!)
                    else -> NetworkResult.Error(getString(R.string.resource_not_found))
                }
            }
            else -> NetworkResult.Error(message())
        }
    }
}
