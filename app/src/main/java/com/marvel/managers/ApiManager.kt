package com.marvel.managers

import com.marvel.retrofit.api.ApiService
import com.marvel.retrofit.api.response.character.Character
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ApiManager @Inject constructor(private val apiService: ApiService) {

    fun callToCharacterServer(
        timestamp: String,
        apiKey: String,
        hash: String,
        limit: Int,
        offset: Int
    ): Observable<ArrayList<Character>> {
        return apiService.getCharacters(timestamp, apiKey, hash, limit, offset)
            .map {
                it.data.results
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}