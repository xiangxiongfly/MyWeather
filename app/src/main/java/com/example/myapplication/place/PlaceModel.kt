package com.example.myapplication.place

import com.example.myapplication.entity.PlaceResult
import com.example.myapplication.network.Api
import com.example.myapplication.network.HttpManager

class PlaceModel {
    suspend fun seachPlaces(query: String): PlaceResult {
        return HttpManager.create(Api::class.java).searchPlaces(query)
    }
}
