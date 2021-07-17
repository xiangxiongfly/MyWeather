package com.example.myapplication.network

import com.example.myapplication.base.BaseApp
import com.example.myapplication.entity.DailyResult
import com.example.myapplication.entity.PlaceResult
import com.example.myapplication.entity.RealtimeResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("v2/place?token=${BaseApp.TOKEN}&lang=zh_CN")
    suspend fun searchPlaces(@Query("query") query: String): PlaceResult

    @GET("v2.5/${BaseApp.TOKEN}/{lng},{lat}/realtime.json")
    suspend fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String,
    ): RealtimeResult

    @GET("v2.5/${BaseApp.TOKEN}/{lng},{lat}/daily.json")
    suspend fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): DailyResult
}