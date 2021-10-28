package io.arunbuilds.rickandmorty.network.api.pagination

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.arunbuilds.rickandmorty.model.response.characters.Character
import io.arunbuilds.rickandmorty.model.response.characters.CharacterResponse
import io.arunbuilds.rickandmorty.network.api.RMCharactersAPI
import io.arunbuilds.rickandmorty.network.api.RMCharactersAPI.Companion.DEFAULT_PAGE_INDEX
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class RMCharactersPagingSource @Inject constructor(
    private val api: RMCharactersAPI
) : RxPagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let {
            val anchorPosition = state.closestPageToPosition(it)
            anchorPosition?.prevKey?.plus(1) ?: anchorPosition?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Character>> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return api.getAllCharacters(page)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, page) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: CharacterResponse, page: Int): LoadResult<Int, Character> {
        return LoadResult.Page(
            data = data.characters ?: emptyList(),
            prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
            nextKey = if (page == data.info?.pages) null else page + 1
        )
    }
}