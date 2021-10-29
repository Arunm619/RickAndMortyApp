package io.arunbuilds.rickandmorty.di

import androidx.paging.PagingConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.arunbuilds.rickandmorty.network.repository.characters.CharactersRepository
import io.arunbuilds.rickandmorty.network.repository.characters.CharactersRepositoryImpl
import io.arunbuilds.rickandmorty.network.repository.episodes.EpisodesRepository
import io.arunbuilds.rickandmorty.network.repository.episodes.EpisodesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsCharactersRepository(
        charactersRepositoryImpl: CharactersRepositoryImpl
    ): CharactersRepository

    @Binds
    abstract fun bindsEpisodesRepository(
        episodesRepositoryImpl: EpisodesRepositoryImpl
    ): EpisodesRepository
}