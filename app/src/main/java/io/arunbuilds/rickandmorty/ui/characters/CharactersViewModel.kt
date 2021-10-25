package io.arunbuilds.rickandmorty.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.arunbuilds.rickandmorty.model.response.characters.CharacterResponse
import io.arunbuilds.rickandmorty.network.repository.CharactersRepository
import io.arunbuilds.rickandmorty.util.SchedulerProvider
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Characters Fragment"
    }
    val text: LiveData<String> = _text

    fun fetchAllCharacters() {
        charactersRepository.getAllCharacters()
            .observeOn(schedulerProvider.io())
            .subscribeOn(schedulerProvider.ui())
            .subscribeWith(object : SingleObserver<CharacterResponse> {
                override fun onSubscribe(d: Disposable) {
                    Timber.d("Subscribed $d")
                }

                override fun onSuccess(t: CharacterResponse) {
                    Timber.d("Wow, the result is $t")
                    _text.postValue( "Wow, the result is $t")
                }

                override fun onError(e: Throwable) {
                    Timber.d("OOPS, the result is $e")
                    _text.postValue( "OOPS, the result is $e")
                }

            })
    }
}