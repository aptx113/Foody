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
package com.danteyu.studio.foody.ui.recipes

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.danteyu.studio.foody.model.Result
import com.danteyu.studio.foody.ui.common.SingleFieldDiffUtil

/**
 * Created by George Yu on 2021/4/1.
 */
class RecipesAdapter :
    ListAdapter<Result, RecipesViewHolder>(SingleFieldDiffUtil<Result> { it.id }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder =
        RecipesViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
