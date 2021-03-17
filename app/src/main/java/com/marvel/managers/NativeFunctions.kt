package com.marvel.managers

object NativeFunctions {
    external fun getPrivateKey(): String
    external fun getPublicKey(): String
}