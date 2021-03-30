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

import com.danteyu.studio.foody.data.source.DataSource
import com.danteyu.studio.foody.data.source.remote.FoodyRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by George Yu on 2021/3/30.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @RemoteDataSource
    @Singleton
    @Binds
    abstract fun bindRemoteDataSource(dataSource: FoodyRemoteDataSource): DataSource
}
