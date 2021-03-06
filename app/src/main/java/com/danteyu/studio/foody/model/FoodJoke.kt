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
import com.danteyu.studio.foody.FOOD_JOKE_TABLE
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Created by George Yu in May. 2021.
 */
@Entity(tableName = FOOD_JOKE_TABLE)
@Parcelize
@JsonClass(generateAdapter = true)
data class FoodJoke(val text: String) : Parcelable {

    @PrimaryKey(autoGenerate = false)
    @IgnoredOnParcel
    var id: Int = 0
}
