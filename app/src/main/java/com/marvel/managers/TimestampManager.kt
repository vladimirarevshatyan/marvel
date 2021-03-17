package com.marvel.managers

import javax.inject.Inject

class TimestampManager @Inject constructor() {

    fun getCurrentTimestamp(): String {
        return System.currentTimeMillis().toString()
    }
}