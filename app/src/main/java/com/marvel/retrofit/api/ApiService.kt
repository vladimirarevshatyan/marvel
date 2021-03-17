package com.marvel.retrofit.api

import com.marvel.retrofit.api.response.Response
import com.marvel.retrofit.api.response.character.Character
import com.marvel.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.MARVEL_CHARACTERS_SUFFIX)
    fun getCharacters(
        @Query("ts") timestamp: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Observable<Response<ArrayList<Character>>>
}