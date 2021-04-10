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
package com.danteyu.studio.foody

/**
 * Created by George Yu on 2021/3/29.
 */
const val BASE_URL = "https://api.spoonacular.com"
const val API_KEY = "79c49f7d6f85422e9fd2c7ba0a52560b"

const val API_TIMEOUT_SECONDS = 15L
const val TIME_OUT = "timeout"
const val PAYMENT_REQUIRED = 402

// API Query Keys
const val QUERY_NUM = "number"
const val QUERY_API_KEY = "apiKey"
const val QUERY_TYPE = "type"
const val QUERY_DIET = "diet"
const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
const val QUERY_FILL_INGREDIENTS = "fillIngredients"

// Database
const val RECIPES_TABLE = "recipes_table"
const val FOODY_DATABASE = "foody_database"

// Bottom Sheet and Preferences
const val USER_PREFERENCES_NAME = "foody_preferences"
const val DEFAULT_RECIPES_NUM = "50"
const val DEFAULT_MEAL_TYPE = "main course"
const val DEFAULT_DIET_TYPE = "gluten free"
const val PREFERENCES_MEAL_TYPE = "mealType"
const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
const val PREFERENCES_DIET_TYPE = "dietType"
const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
const val PREFERENCES_BACK_ONLINE = "backOnline"
