package io.arunbuilds.rickandmorty.network.api

import io.arunbuilds.rickandmorty.model.response.characters.CharacterResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RMCharactersAPI {
    @GET("/api/character")
    fun getAllCharacters(@Query("page") page: Int): Single<CharacterResponse>

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }
}