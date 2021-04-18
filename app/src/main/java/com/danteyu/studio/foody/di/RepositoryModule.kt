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
package com.danteyu.studio.foody.di

import com.danteyu.studio.foody.data.repository.FoodyRepository
import com.danteyu.studio.foody.data.source.api.FoodyApiService
import com.danteyu.studio.foody.data.source.db.RecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by George Yu in 4月. 2021.
 */
@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideFoodyRepository(apiService: FoodyApiService, dao: RecipesDao) =
        FoodyRepository(apiService, dao)
}
