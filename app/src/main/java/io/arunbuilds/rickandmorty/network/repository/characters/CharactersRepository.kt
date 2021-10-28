package io.arunbuilds.rickandmorty.network.repository.characters

import androidx.paging.PagingData
import io.arunbuilds.rickandmorty.model.response.characters.Character
import io.reactivex.rxjava3.core.Flowable

interface CharactersRepository {
    fun getAllCharacters(): Flowable<PagingData<Character>>
}