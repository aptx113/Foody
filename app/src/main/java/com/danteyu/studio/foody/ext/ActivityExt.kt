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

import android.app.Activity
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

/**
 * Created by George Yu in Apr. 2021.
 */
fun Activity.showSnackBar(
    view: View,
    message: String,
    actionTitle: String,
    action: () -> Unit = {}
) =
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction(actionTitle) { action() }.show()
fun Activity.applyStatusBarColor(@ColorRes color: Int) {
    this.window.statusBarColor = ContextCompat.getColor(this, color)
}
