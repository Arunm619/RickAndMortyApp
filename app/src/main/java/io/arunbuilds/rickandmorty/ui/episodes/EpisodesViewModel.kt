package io.arunbuilds.rickandmorty.ui.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EpisodesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Episodes Fragment"
    }
    val text: LiveData<String> = _text
}