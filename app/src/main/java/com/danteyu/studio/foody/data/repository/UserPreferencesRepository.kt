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
package com.danteyu.studio.foody.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.danteyu.studio.foody.DEFAULT_DIET_TYPE
import com.danteyu.studio.foody.DEFAULT_MEAL_TYPE
import com.danteyu.studio.foody.PREFERENCES_DIET_TYPE
import com.danteyu.studio.foody.PREFERENCES_DIET_TYPE_ID
import com.danteyu.studio.foody.PREFERENCES_MEAL_TYPE
import com.danteyu.studio.foody.PREFERENCES_MEAL_TYPE_ID
import com.danteyu.studio.foody.USER_PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

/**
 * Created by George Yu in 4月. 2021.
 */
@ViewModelScoped
class UserPreferencesRepository @Inject constructor(@ApplicationContext context: Context) :
    Repository {

    private val preferencesDataStore = context.dataStore

    private object PreferenceKeys {
        val SELECTED_MEAL_TYPE = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val SELECTED_MEAL_TYPE_ID = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val SELECTED_DIET_TYPE = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val SELECTED_DIET_TYPE_ID = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }

    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) = preferencesDataStore.edit { preferences ->
        preferences[PreferenceKeys.SELECTED_MEAL_TYPE] = mealType
        preferences[PreferenceKeys.SELECTED_MEAL_TYPE_ID] = mealTypeId
        preferences[PreferenceKeys.SELECTED_DIET_TYPE] = dietType
        preferences[PreferenceKeys.SELECTED_DIET_TYPE_ID] = dietTypeId
    }

    val mealAndDietFlow: Flow<MealAndDietType> = preferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val selectedMealType =
                preferences[PreferenceKeys.SELECTED_MEAL_TYPE] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.SELECTED_MEAL_TYPE_ID] ?: 0
            val selectedDietType =
                preferences[PreferenceKeys.SELECTED_DIET_TYPE] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.SELECTED_DIET_TYPE_ID] ?: 0

            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)
