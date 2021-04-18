package com.danteyu.studio.foody.ext

import android.app.Activity
import android.view.View
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
