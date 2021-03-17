package com.marvel.utils


import android.util.Log
import com.marvel.BuildConfig
import java.lang.Exception
import javax.inject.Inject

class Logger @Inject constructor() {
    fun logInDebug(exception: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(Logger::class.java.name, exception.message.toString())
        }
    }

    fun logInDebug(value: String) {
        if (BuildConfig.DEBUG) {
            Log.d(Logger::class.java.name, value)
        }
    }
}