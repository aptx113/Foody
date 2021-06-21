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
package com.danteyu.studio.foody.ui.details.instructions

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.danteyu.studio.foody.RECIPE_RESULT_KEY
import com.danteyu.studio.foody.databinding.FragmentInstructionsBinding
import com.danteyu.studio.foody.model.FoodRecipe

class InstructionsFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentInstructionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewDataBinding = FragmentInstructionsBinding.inflate(layoutInflater, container, false)

        val foodRecipe: FoodRecipe? = requireArguments().getParcelable(RECIPE_RESULT_KEY)

        viewDataBinding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                viewDataBinding.progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                viewDataBinding.progressBar.visibility = View.INVISIBLE
            }
        }
        if (foodRecipe?.sourceUrl != null) viewDataBinding.webView.loadUrl(foodRecipe.sourceUrl)

        return viewDataBinding.root
    }
}
