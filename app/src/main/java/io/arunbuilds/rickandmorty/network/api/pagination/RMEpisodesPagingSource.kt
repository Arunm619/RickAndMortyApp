package io.arunbuilds.rickandmorty.network.api.pagination

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.arunbuilds.rickandmorty.model.response.episodes.Episode
import io.arunbuilds.rickandmorty.model.response.episodes.EpisodeResponse
import io.arunbuilds.rickandmorty.network.api.RMCharactersAPI.Companion.DEFAULT_PAGE_INDEX
import io.arunbuilds.rickandmorty.network.api.RMEpisodesAPI
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class RMEpisodesPagingSource @Inject constructor(
    private val api: RMEpisodesAPI
) : RxPagingSource<Int, Episode>() {

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let {
            val anchorPosition = state.closestPageToPosition(it)
            anchorPosition?.prevKey?.plus(1) ?: anchorPosition?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Episode>> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return api.getAllEpisodes(page)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, page) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: EpisodeResponse, page: Int): LoadResult<Int, Episode> {
        return LoadResult.Page(
            data = data.episodes ?: emptyList(),
            prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
            nextKey = if (page == data.info?.pages) null else page + 1
        )
    }
}