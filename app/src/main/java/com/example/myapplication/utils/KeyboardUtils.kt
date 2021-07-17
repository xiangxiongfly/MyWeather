package com.example.myapplication.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.myapplication.base.BaseApp

object KeyboardUtils {
    fun close(view: View) {
        val manager =
            BaseApp.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS)
    }
}