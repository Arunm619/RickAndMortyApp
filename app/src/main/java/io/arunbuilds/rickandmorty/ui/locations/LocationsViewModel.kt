package io.arunbuilds.rickandmorty.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Locations Fragment"
    }
    val text: LiveData<String> = _text
}