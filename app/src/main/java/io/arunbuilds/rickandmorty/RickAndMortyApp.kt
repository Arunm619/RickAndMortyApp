package io.arunbuilds.rickandmorty

import android.app.Application
import timber.log.Timber

class RickAndMortyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.d("RickAndMortyApp created.")
    }
}