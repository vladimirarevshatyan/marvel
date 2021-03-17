package com.marvel

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.marvel.dagger.components.AppComponent
import com.marvel.dagger.components.DaggerAppComponent
import com.marvel.managers.NativeLibManager
import io.realm.Realm

class MainApp : MultiDexApplication() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        NativeLibManager().loadLibrary()
        initDagger()
        Realm.init(this)
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.create()
    }
}