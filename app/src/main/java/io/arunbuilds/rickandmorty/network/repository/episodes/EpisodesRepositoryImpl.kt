package io.arunbuilds.rickandmorty.network.repository.episodes

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import io.arunbuilds.rickandmorty.model.response.episodes.Episode
import io.arunbuilds.rickandmorty.network.api.pagination.RMEpisodesPagingSource
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val pagingSource: RMEpisodesPagingSource
) : EpisodesRepository {
    override fun getAllEpisodes(): Flowable<PagingData<Episode>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                maxSize = 20,
                prefetchDistance = 5,
                initialLoadSize = 10
            ), pagingSourceFactory = { pagingSource }
        ).flowable
    }
}