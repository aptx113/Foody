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
package com.danteyu.studio.foody.ui.details.ingredients

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.danteyu.studio.foody.model.ExtendedIngredient
import com.danteyu.studio.foody.ui.common.SingleFieldDiffUtil
import java.util.Locale

/**
 * Created by George Yu in Apr. 2021.
 */
class IngredientsAdapter :
    ListAdapter<ExtendedIngredient, IngredientsViewHolder>(SingleFieldDiffUtil<ExtendedIngredient> { it.name }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder =
        IngredientsViewHolder.create(parent)

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.bind(ingredient)
        holder.viewDataBinding.nameTxt.text = ingredient.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
    }
}
