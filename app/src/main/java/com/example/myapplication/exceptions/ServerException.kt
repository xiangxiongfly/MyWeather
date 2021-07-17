package com.example.myapplication.exceptions

/**
 * 服务器返回错误数据
 */
class ServerException(val status: String) : RuntimeException()