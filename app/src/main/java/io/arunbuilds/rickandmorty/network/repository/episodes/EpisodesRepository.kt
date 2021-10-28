package io.arunbuilds.rickandmorty.network.repository.episodes

import androidx.paging.PagingData
import io.arunbuilds.rickandmorty.model.response.characters.Character
import io.arunbuilds.rickandmorty.model.response.episodes.Episode
import io.reactivex.rxjava3.core.Flowable

interface EpisodesRepository {
    fun getAllEpisodes(): Flowable<PagingData<Episode>>
}