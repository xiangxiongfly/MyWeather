package com.example.myapplication.base

open class BaseModel {
//    suspend inline fun <T> launchRequestForResult(noinline block: suspend () -> T): DataResult<T> {
//        return try {
//            val response = block.invoke()
//            if ((response as BaseResponse<*>).isSuccessful()) {
//                DataResult.Success(response)
//            } else {
//                DataResult.Error(
//                    ExceptionHandler.handleException(
//                        ServerException(response.status)
//                    )
//                )
//            }
//        } catch (e: Exception) {
//            return DataResult.Error(ExceptionHandler.handleException(e))
//        }
//    }
}