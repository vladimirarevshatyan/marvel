package com.marvel.fragments.communicators

interface Communicator<CommunicatorType> {
    fun setCommunicator(communicator: CommunicatorType)
}