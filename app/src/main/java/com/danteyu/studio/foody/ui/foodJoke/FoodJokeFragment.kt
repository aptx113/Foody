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
package com.danteyu.studio.foody.ui.foodJoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.danteyu.studio.foody.API_KEY
import com.danteyu.studio.foody.databinding.FragmentFoodJokeBinding
import com.danteyu.studio.foody.ext.observeInLifecycle
import com.danteyu.studio.foody.ext.observeOnce
import com.danteyu.studio.foody.ext.showToast
import com.danteyu.studio.foody.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private val viewModel: FoodJokeViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentFoodJokeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewDataBinding = FragmentFoodJokeBinding.inflate(layoutInflater, container, false)

        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getFoodJoke(API_KEY)
        viewModel.foodJokeRequestFlow.onEach { response ->
            when (response) {
                is NetworkResult.Success -> {
                    viewDataBinding.foodJokeTxt.text = response.data?.text
                    viewDataBinding.foodJokeCardView.visibility = View.VISIBLE
                    viewDataBinding.foodJokeProgress.visibility = View.INVISIBLE
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    showToast(response.message.toString())
                    viewDataBinding.foodJokeProgress.visibility = View.INVISIBLE
                }
                is NetworkResult.Loading -> {
                    Timber.d("Loading")
                    viewDataBinding.foodJokeCardView.visibility = View.INVISIBLE
                    viewDataBinding.foodJokeProgress.visibility = View.VISIBLE
                }
            }
        }.observeInLifecycle(viewLifecycleOwner)
    }

    private fun loadDataFromCache() {
        lifecycleScope.launchWhenStarted {
            viewModel.foodJokeResult.observeOnce(viewLifecycleOwner) {
                if (!it.isNullOrEmpty()) {
                    viewDataBinding.foodJokeTxt.text = it[0].text
                }
                viewDataBinding.foodJokeCardView.visibility = View.VISIBLE
                if (it != null)
                    if (it.isEmpty()) viewDataBinding.foodJokeCardView.visibility = View.INVISIBLE
            }
        }
    }
}
