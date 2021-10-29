package io.arunbuilds.rickandmorty.network.repository.characters

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import io.arunbuilds.rickandmorty.model.response.characters.Character
import io.arunbuilds.rickandmorty.network.api.pagination.RMCharactersPagingSource
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val pagingSource: RMCharactersPagingSource,
    private val pagingConfig: PagingConfig
) : CharactersRepository {
    override fun getAllCharacters(): Flowable<PagingData<Character>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { pagingSource }
        ).flowable
    }
}