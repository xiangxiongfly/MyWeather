package com.example.myapplication.place

import androidx.lifecycle.ViewModel
import com.example.myapplication.SingleLiveData
import com.example.myapplication.entity.PlaceResult
import com.example.myapplication.exceptions.ExceptionHandler
import com.example.myapplication.exceptions.ServerException
import com.example.myapplication.extensions.launchMain
import com.example.myapplication.network.DataResult

class PlaceViewModel : ViewModel() {

    private val searchLiveData = SingleLiveData<DataResult<PlaceResult>>()

    fun searchPlaces(query: String): SingleLiveData<DataResult<PlaceResult>> {
        launchMain {
            val placeModel = PlaceModel()
            try {
                val searchPlaces = placeModel.seachPlaces(query)
                if ("ok" == searchPlaces.status) {
                    searchLiveData.value = DataResult.Success(searchPlaces)
                } else {
                    searchLiveData.value =
                        DataResult.Error(ExceptionHandler.handleException(ServerException(
                            searchPlaces.status)))
                }
            } catch (e: Exception) {
                searchLiveData.value = DataResult.Error(ExceptionHandler.handleException(e))
            }
        }
        return searchLiveData
    }

}