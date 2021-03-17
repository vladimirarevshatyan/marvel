package com.marvel.fragments.communicators

import com.marvel.retrofit.api.response.character.Character

interface CharacterInfoCommunicator {
    fun groupCharacters(character: Character?)
    fun onGetCharacter(characterId: Long)
}