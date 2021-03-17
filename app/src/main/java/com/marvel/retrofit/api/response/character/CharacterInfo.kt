package com.marvel.retrofit.api.response.character

import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize

open class CharacterInfo(var items: RealmList<CharacterInfoItem> = RealmList<CharacterInfoItem>()) :
    RealmObject()