package com.example.myapplication.entity

import com.google.gson.annotations.SerializedName

data class PlaceResult(
    val places: ArrayList<Place>,
    val query: String,
    val status: String
)

data class Place(
    @SerializedName("formatted_address")
    val address: String,
    val id: String,
    val location: Location,
    val name: String,
    val place_id: String,
)

data class Location(
    val lat: String,
    val lng: String,
)