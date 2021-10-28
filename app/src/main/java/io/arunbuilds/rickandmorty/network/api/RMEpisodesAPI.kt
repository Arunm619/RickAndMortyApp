package io.arunbuilds.rickandmorty.network.api

import io.arunbuilds.rickandmorty.model.response.characters.CharacterResponse
import io.arunbuilds.rickandmorty.model.response.episodes.EpisodeResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RMEpisodesAPI {
    @GET("/api/episode")
    fun getAllEpisodes(@Query("page") page: Int): Single<EpisodeResponse>

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }
}