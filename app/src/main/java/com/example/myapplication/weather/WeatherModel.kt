package com.example.myapplication.weather

import com.example.myapplication.entity.DailyResult
import com.example.myapplication.entity.RealtimeResult
import com.example.myapplication.network.Api
import com.example.myapplication.network.HttpManager

class WeatherModel {
    suspend fun getRealtimeWeather(lng: String, lat: String): RealtimeResult {
        return HttpManager.create(Api::class.java).getRealtimeWeather(lng, lat)
    }

    suspend fun getDailyWeather(lng: String, lat: String): DailyResult {
        return HttpManager.create(Api::class.java).getDailyWeather(lng, lat)
    }

}