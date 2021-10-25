package io.arunbuilds.rickandmorty.network.repository

import io.arunbuilds.rickandmorty.network.api.RickAndMortyAPI
import io.arunbuilds.rickandmorty.network.api.RickAndMortyAPI.Companion.DEFAULT_PAGE_NUMBER
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val api: RickAndMortyAPI
) {
    fun getAllCharacters(page: Int = DEFAULT_PAGE_NUMBER) = api.getAllCharacters(page)
}