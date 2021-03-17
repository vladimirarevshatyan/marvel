package com.marvel.retrofit.api.response.character

import io.realm.RealmObject

open class Thumbnail(var path: String = "", var extension: String = "") : RealmObject()