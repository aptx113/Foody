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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.danteyu.studio.foody.DEFAULT_DIET_TYPE
import com.danteyu.studio.foody.DEFAULT_MEAL_TYPE
import com.danteyu.studio.foody.databinding.FragRecipeBottomSheetBinding
import com.danteyu.studio.foody.ext.observeInLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.Locale

/**
 * Created by George Yu in Apr. 2021.
 */
@SuppressWarnings("TooGenericExceptionCaught")
class RecipesBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var viewDataBinding: FragRecipeBottomSheetBinding
    private val viewModel by activityViewModels<RecipesViewModel>()

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragRecipeBottomSheetBinding.inflate(layoutInflater, container, false)
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mealAndDietTypeFlow.onEach {
            mealTypeChip = it.selectedMealType
            dietTypeChip = it.selectedDietType
            updateChip(it.selectedMealTypeId, viewDataBinding.mealTypeChipGroup)
            updateChip(it.selectedDietTypeId, viewDataBinding.dietTypeChipGroup)
        }.observeInLifecycle(viewLifecycleOwner)

        viewDataBinding.mealTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedMealType = chip.text.toString().toLowerCase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = checkedId
        }

        viewDataBinding.dietTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedDietType = chip.text.toString().toLowerCase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId = checkedId
        }
        viewModel.applySelectedChipsFlow
            .onEach {
                if (it) {
                    viewModel.saveMealAndDietType(
                        mealTypeChip,
                        mealTypeChipId,
                        dietTypeChip,
                        dietTypeChipId
                    )
                    findNavController().navigate(
                        RecipesBottomSheetFragmentDirections.actionRecipesBottomSheetFragmentToRecipesFragment(
                            true
                        )
                    )
                }
            }.observeInLifecycle(viewLifecycleOwner)
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                val targetView = chipGroup.findViewById<Chip>(chipId)
                targetView.isChecked = true
                chipGroup.requestChildFocus(targetView, targetView)
            } catch (e: Exception) {
                Timber.d(e.message.toString())
            }
        }
    }
}
