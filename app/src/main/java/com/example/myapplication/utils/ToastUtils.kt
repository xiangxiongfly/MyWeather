package com.example.myapplication.utils

import android.content.Context
import android.widget.Toast

fun toast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}