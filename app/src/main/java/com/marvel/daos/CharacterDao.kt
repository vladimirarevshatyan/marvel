package com.marvel.daos

import com.marvel.retrofit.api.response.character.Character
import io.realm.Realm
import io.realm.RealmList
import javax.inject.Inject

class CharacterDao @Inject constructor(private val realm: Realm) {

    fun getCharacter(characterId: Long): Character {
        return realm.where(Character::class.java).equalTo("id", characterId)
            .findFirst() ?: Character()
    }

    fun saveCharacters(characters: RealmList<Character>) {
        realm.executeTransactionAsync {
            it.copyToRealmOrUpdate(characters)
        }

    }
}