package com.marvel.fragments.communicators

import com.marvel.retrofit.api.response.character.Character

interface CharacterFragmentCommunicator {
    fun onRefreshData()
    fun onCharacterClicked(character: Character)
    fun onNewDataRequest()
}