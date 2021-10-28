package io.arunbuilds.rickandmorty.ui.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.arunbuilds.rickandmorty.model.response.episodes.Episode
import io.arunbuilds.rickandmorty.network.repository.episodes.EpisodesRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodesRepository: EpisodesRepository
) : ViewModel() {
    private val bag = CompositeDisposable()

    private val _episodes = MutableLiveData<PagingData<Episode>>()
    val episodes: LiveData<PagingData<Episode>> get() = _episodes


    init {
        fetchAllEpisodes()
    }

    private fun fetchAllEpisodes() {
        bag.add(
            episodesRepository.getAllEpisodes()
                .cachedIn(viewModelScope)
                .subscribe {
                    _episodes.value = it
                }
        )
    }

    fun onRefresh() {
        fetchAllEpisodes()
    }

    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }
}