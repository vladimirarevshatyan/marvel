package com.marvel.dagger.components

import com.marvel.MainActivity
import com.marvel.dagger.modules.DatabaseModules
import com.marvel.dagger.modules.NetworkModules
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModules::class, DatabaseModules::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}