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
package com.danteyu.studio.foody.ui.foodJoke

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.danteyu.studio.foody.model.FoodJoke
import com.danteyu.studio.foody.utils.NetworkResult

/**
 * Created by George Yu in May. 2021.
 */
object FoodJokeBinding {

    @BindingAdapter("foodJokeApiRequest", "foodJokeResult", requireAll = true)
    @JvmStatic
    fun bindErrorViewsVisibility(
        view: View,
        foodJokeApiRequest: NetworkResult<FoodJoke>?,
        foodJokeResult: List<FoodJoke>?
    ) {
        when {
            foodJokeResult != null && foodJokeResult.isEmpty() -> {
                view.visibility = View.VISIBLE
                if (view is TextView && foodJokeApiRequest != null) {
                    view.text = foodJokeApiRequest.message.toString()
                }
            }
            foodJokeApiRequest is NetworkResult.Success -> view.visibility = View.INVISIBLE
            foodJokeApiRequest is NetworkResult.Loading -> view.visibility = View.INVISIBLE
        }
    }
}
