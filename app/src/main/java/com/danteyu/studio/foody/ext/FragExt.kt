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
package com.danteyu.studio.foody.ext

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danteyu.studio.foody.model.FoodRecipe
import com.danteyu.studio.foody.ui.details.DetailsAssistedFactory
import com.google.android.material.snackbar.Snackbar

/**
 * Created by George Yu in Apr. 2021.
 */
fun Fragment.showToast(message: String) =
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

fun Fragment.showSnackBar(
    view: View,
    message: String,
    actionTitle: String,
    action: () -> Unit = {}
) =
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction(actionTitle) { action() }.show()

@Suppress("UNCHECKED_CAST")
fun Fragment.provideDetailsFactory(
    assistedFactory: DetailsAssistedFactory,
    foodRecipe: FoodRecipe?
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return assistedFactory.create(foodRecipe) as T
    }
}
