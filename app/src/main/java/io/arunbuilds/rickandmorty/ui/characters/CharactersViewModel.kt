package io.arunbuilds.rickandmorty.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.arunbuilds.rickandmorty.model.response.characters.Character
import io.arunbuilds.rickandmorty.network.repository.characters.CharactersRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
) : ViewModel() {

    private val bag = CompositeDisposable()

    private val _characters = MutableLiveData<PagingData<Character>>()
    val characters: LiveData<PagingData<Character>> get() = _characters


    init {
        fetchAllCharacters()
    }

    private fun fetchAllCharacters() {
        bag.add(
            charactersRepository.getAllCharacters()
                .cachedIn(viewModelScope)
                .subscribe {
                    _characters.value = it
                }
        )
    }

    fun onRefresh() {
        fetchAllCharacters()
    }

    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }
}