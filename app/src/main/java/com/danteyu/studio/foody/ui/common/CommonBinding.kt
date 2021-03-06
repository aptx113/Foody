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
package com.danteyu.studio.foody.ui.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.danteyu.studio.foody.CROSS_FADE_IN_MILLIS
import com.danteyu.studio.foody.R
import com.danteyu.studio.foody.model.FoodRecipesResponse
import com.danteyu.studio.foody.utils.NetworkResult
import org.jsoup.Jsoup

/**
 * Created by George Yu on 2021/4/2.
 */
object CommonBinding {

    @BindingAdapter("imgUrl")
    @JvmStatic
    fun bindImageFromUrl(imageView: ImageView, imageUrl: String) =
        imageView.load(imageUrl) {
            crossfade(CROSS_FADE_IN_MILLIS)
            error(R.drawable.ic_error_placeholder)
        }

    @BindingAdapter("apiResponse", "foodRecipes", requireAll = true)
    @JvmStatic
    fun bindErrorVisibility(
        view: View,
        apiResponse: NetworkResult<FoodRecipesResponse>?,
        foodRecipes: List<FoodRecipesResponse>?
    ) {
        when (view) {
            is ImageView ->
                view.isVisible =
                    apiResponse is NetworkResult.Error && foodRecipes.isNullOrEmpty()

            is TextView -> {
                view.isVisible = apiResponse is NetworkResult.Error && foodRecipes.isNullOrEmpty()
                view.text = apiResponse?.message.toString()
            }
        }
    }

    @BindingAdapter("parseHtml")
    @JvmStatic
    fun bindParseHtml(textView: TextView, description: String?) {
        if (description != null) {
            val desc = Jsoup.parse(description).text()
            textView.text = desc
        }
    }

    @BindingAdapter("visibleGone")
    @JvmStatic
    fun bindVisibility(view: View, condition: Boolean) {
        if (condition) view.visibility = View.GONE
        else view.visibility = View.VISIBLE
    }
}
