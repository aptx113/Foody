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

import com.danteyu.studio.foody.API_TIMEOUT_SECONDS
import com.danteyu.studio.foody.BASE_URL
import com.danteyu.studio.foody.BuildConfig
import com.danteyu.studio.foody.data.source.api.FoodyApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by George Yu on 2021/3/29.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                }
            )
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FoodyApiService =
        retrofit.create(FoodyApiService::class.java)
}
