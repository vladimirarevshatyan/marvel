package com.marvel.view_models

import androidx.lifecycle.MutableLiveData
import com.marvel.daos.CharacterDao
import com.marvel.managers.ApiManager
import com.marvel.managers.NativeFunctions
import com.marvel.managers.TimestampManager
import com.marvel.retrofit.api.response.character.Character
import com.marvel.security.MDHashCreator
import io.reactivex.rxjava3.core.Observable
import io.realm.RealmList
import javax.inject.Inject

class MainVM @Inject constructor(
    private val mdHashCreator: MDHashCreator,
    private val apiManager: ApiManager,
    private val timestampManager: TimestampManager,
    private val characterDao: CharacterDao
) {

    var charactersListLiveData = MutableLiveData<ArrayList<Character>?>()
    var groupedCharactersLiveData = MutableLiveData<HashMap<String, ArrayList<String>>>()
    var dataAvailability = MutableLiveData<Boolean>()
    var characterLiveData = MutableLiveData<Character>()

    fun getCharacters(limit: Int, offset: Int): Observable<ArrayList<Character>> {
        return apiManager.callToCharacterServer(
            timestampManager.getCurrentTimestamp(),
            NativeFunctions.getPublicKey(), generateMD5(), limit, offset
        )
    }

    private fun generateMD5(): String {
        val currentTimestamp = System.currentTimeMillis().toString()
        val publicKey = NativeFunctions.getPublicKey()
        val privateKey = NativeFunctions.getPrivateKey()
        val finalResultToHash = currentTimestamp + privateKey + publicKey
        return mdHashCreator.createMD5(finalResultToHash)
    }

    fun postCharacters(it: ArrayList<Character>?) {
        charactersListLiveData.value = it
    }

    fun postNoNewDataAvailable() {
        dataAvailability.value = false
    }

    private fun postCharacter(character: Character) {
        characterLiveData.value = character
    }

    fun getCharacter(characterId: Long) {
        postCharacter(characterDao.getCharacter(characterId))
    }

    fun groupCharacters(character: Character?) {
        character?.let {
            val stories = ArrayList<String>()
            val events = ArrayList<String>()
            val comics = ArrayList<String>()
            val series = ArrayList<String>()

            for (story in character.stories.items) {
                stories.add(story.name)
            }

            for (event in character.events.items) {
                events.add(event.name)
            }

            for (comic in character.comics.items) {
                comics.add(comic.name)
            }

            for (singleSeries in character.series.items) {
                series.add(singleSeries.name)
            }

            val finalResult = HashMap<String, ArrayList<String>>()
            finalResult["Stories"] = stories
            finalResult["Events"] = events
            finalResult["Comics"] = comics
            finalResult["Series"] = series

            groupedCharactersLiveData.value = finalResult
        }
    }

    fun saveCharacters(result: ArrayList<Character>) {
        val realmList = RealmList<Character>()
        realmList.addAll(result)
        characterDao.saveCharacters(realmList)
    }
}