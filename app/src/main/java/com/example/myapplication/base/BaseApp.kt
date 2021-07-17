package com.example.myapplication.base

import android.app.Application

class BaseApp : Application() {
    companion object {
        const val TOKEN = "wzVnmBeTsNzVe66C"
        lateinit var context: BaseApp
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}