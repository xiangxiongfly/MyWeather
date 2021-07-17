package com.example.myapplication.weather

import androidx.lifecycle.ViewModel
import com.example.myapplication.SingleLiveData
import com.example.myapplication.entity.Weather
import com.example.myapplication.exceptions.ExceptionHandler
import com.example.myapplication.exceptions.ServerException
import com.example.myapplication.extensions.launchMain
import com.example.myapplication.network.DataResult
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope

class WeatherViewModel : ViewModel() {
    var lng = ""
    var lat = ""
    var placeName = ""

    private val weatherLiveData = SingleLiveData<DataResult<Weather>>()

    fun refreshWeather(lng: String, lat: String): SingleLiveData<DataResult<Weather>> {
        val weatherModel = WeatherModel()

        launchMain {
            supervisorScope {
                try {
                    val realtimeDeferred = async { weatherModel.getRealtimeWeather(lng, lat) }
                    val dailyDeferred = async { weatherModel.getDailyWeather(lng, lat) }
                    val realtimeResult = realtimeDeferred.await()
                    val dailyResult = dailyDeferred.await()
                    if (realtimeResult.status == "ok" && dailyResult.status == "ok") {
                        weatherLiveData.value =
                            DataResult.Success(Weather(realtimeResult, dailyResult))
                    } else {
                        weatherLiveData.value = DataResult.Error(ExceptionHandler.handleException(
                            ServerException("realtime response status is ${realtimeResult.status} , daily response status is ${dailyResult.status}")
                        ))
                    }
                } catch (e: Exception) {
                    weatherLiveData.value = DataResult.Error(ExceptionHandler.handleException(e))
                }
            }
        }
        return weatherLiveData
    }

}