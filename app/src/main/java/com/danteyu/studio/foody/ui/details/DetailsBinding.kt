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
package com.danteyu.studio.foody.ui.details

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.danteyu.studio.foody.BASE_IMAGE_URL
import com.danteyu.studio.foody.CROSS_FADE_IN_MILLIS
import com.danteyu.studio.foody.R

/**
 * Created by George Yu in Apr. 2021.
 */
object DetailsBinding {

    @BindingAdapter("stateIsOn")
    @JvmStatic
    fun bindStateColor(view: View, stateIsOn: Boolean) {
        if (stateIsOn) {
            when (view) {
                is TextView -> view.setTextColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.green
                    )
                )
                is ImageView -> view.setColorFilter(
                    ContextCompat.getColor(
                        view.context,
                        R.color.green
                    )
                )
            }
        }
    }

    @BindingAdapter("ingredientImg")
    @JvmStatic
    fun bindIngredientImage(imageView: ImageView, imgUrl: String?) {
        imageView.load(BASE_IMAGE_URL + imgUrl) {
            crossfade(CROSS_FADE_IN_MILLIS)
            error(R.drawable.ic_error_placeholder)
        }
    }
}
