package com.example.myapplication

import android.content.Context
import androidx.core.content.edit
import com.example.myapplication.base.BaseApp
import com.example.myapplication.entity.Place
import com.google.gson.Gson

object DataCache {
    private fun getSP() = BaseApp.context.getSharedPreferences("app", Context.MODE_PRIVATE)

    fun savePlace(place: Place) {
        getSP().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getPlace(): Place {
        val placeStr = getSP().getString("place", "")
        return Gson().fromJson(placeStr, Place::class.java)
    }

    fun isSaved() = getSP().contains("place")
}