package com.example.myapplication.network

import com.example.myapplication.exceptions.ApiException

/**
 * 用于判断接口是否成功调用
 */
sealed class DataResult<out T> {
    data class Success<T>(val response: T) : DataResult<T>()
    data class Error(val exception: ApiException) : DataResult<Nothing>()
}