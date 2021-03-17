package com.marvel.retrofit.api.response.character

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Character(
    @PrimaryKey
    var id: Long = 0,
    var name: String = "",
    var description: String = "",
    var thumbnail: Thumbnail = Thumbnail(),
    var comics: CharacterInfo = CharacterInfo(),
    var series: CharacterInfo = CharacterInfo(),
    var stories: CharacterInfo = CharacterInfo(),
    var events: CharacterInfo = CharacterInfo()
) : RealmObject()