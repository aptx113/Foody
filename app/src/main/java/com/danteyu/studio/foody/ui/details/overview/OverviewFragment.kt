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
package com.danteyu.studio.foody.ui.details.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.danteyu.studio.foody.RECIPE_RESULT_KEY
import com.danteyu.studio.foody.databinding.FragmentOverviewBinding
import com.danteyu.studio.foody.ext.provideDetailsFactory
import com.danteyu.studio.foody.ui.details.DetailsAssistedFactory
import com.danteyu.studio.foody.ui.details.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    @Inject
    lateinit var detailsViewModelFactory: DetailsAssistedFactory

    private val detailsViewModel by activityViewModels<DetailsViewModel> {
        provideDetailsFactory(
            detailsViewModelFactory,
            requireArguments().getParcelable(
                RECIPE_RESULT_KEY
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewDataBinding = FragmentOverviewBinding.inflate(layoutInflater, container, false)
        viewDataBinding.detailsViewModel = detailsViewModel
        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        return viewDataBinding.root
    }
}
