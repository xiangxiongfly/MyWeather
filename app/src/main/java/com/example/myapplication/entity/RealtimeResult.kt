package com.example.myapplication.entity

import com.google.gson.annotations.SerializedName

class RealtimeResult(val status: String, val result: Result) {

    class Result(val realtime: Realtime)

    class Realtime(
        val skycon: String,
        val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality,
    )

    class AirQuality(val aqi: AQI)

    class AQI(val chn: Float)
}