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

import android.content.Context
import androidx.room.Room
import com.danteyu.studio.foody.FOODY_DATABASE
import com.danteyu.studio.foody.data.source.db.FoodyDatabase
import com.danteyu.studio.foody.data.source.db.RecipesConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by George Yu in Apr. 2021.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        recipesConverters: RecipesConverters
    ) = Room.databaseBuilder(
        context,
        FoodyDatabase::class.java,
        FOODY_DATABASE
    ).addTypeConverter(recipesConverters)
        .build()

    @Singleton
    @Provides
    fun provideDao(database: FoodyDatabase) = database.recipesDao()
}
