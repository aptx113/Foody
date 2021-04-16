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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danteyu.studio.foody.databinding.ItemIngredientsBinding
import com.danteyu.studio.foody.model.ExtendedIngredient

/**
 * Created by George Yu in Apr. 2021.
 */
class IngredientsViewHolder(val viewDataBinding: ItemIngredientsBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(ingredients: ExtendedIngredient) {
        viewDataBinding.ingredient = ingredients
        viewDataBinding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): IngredientsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemIngredientsBinding.inflate(layoutInflater, parent, false)
            return IngredientsViewHolder(binding)
        }
    }
}
