package com.marvel.security

import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

class MDHashCreator @Inject constructor() {

    fun createMD5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}