package com.example.myapplication.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.exceptions.coroutineExceptionHandler
import kotlinx.coroutines.*

inline fun ViewModel.launchMain(noinline block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(coroutineExceptionHandler) {
        block.invoke(this)
    }
}

inline fun ViewModel.launchIO(noinline block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
        block.invoke(this)
    }
}

inline fun ViewModel.delayLaunchIO(
    delayTime: Long,
    noinline block: suspend CoroutineScope.() -> Unit,
) {
    viewModelScope.launch(coroutineExceptionHandler) {
        withContext(Dispatchers.IO) {
            delay(delayTime)
        }
        block.invoke(this)
    }
}