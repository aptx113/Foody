package com.danteyu.studio.foody.utils

/**
 * Created by George Yu on 2021/3/29.
 */
sealed class NetworkResult<out R>

data class Success<out T>(val data: T) : NetworkResult<T>()
data class Error(val message: String?) : NetworkResult<Nothing>()
object Loading : NetworkResult<Nothing>()
