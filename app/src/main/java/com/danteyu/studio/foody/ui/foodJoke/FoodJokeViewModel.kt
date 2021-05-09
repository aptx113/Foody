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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.danteyu.studio.foody.data.repository.FoodyRepository
import com.danteyu.studio.foody.model.FoodJoke
import com.danteyu.studio.foody.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by George Yu in May. 2021.
 */
@HiltViewModel
class FoodJokeViewModel @Inject constructor(private val repository: FoodyRepository) : ViewModel() {

    private val _foodJokeRequestFlow =
        MutableStateFlow<NetworkResult<FoodJoke>>(NetworkResult.Loading())
    val foodJokeRequestFlow: StateFlow<NetworkResult<FoodJoke>> = _foodJokeRequestFlow

    val foodJokeResult = repository.loadFoodJokeFlow().asLiveData()

    fun getFoodJoke(apiKey: String) = viewModelScope.launch {
        _foodJokeRequestFlow.value = NetworkResult.Loading()
        repository.getFoodJokeFlow(apiKey).collect { _foodJokeRequestFlow.value = it }
    }
}
