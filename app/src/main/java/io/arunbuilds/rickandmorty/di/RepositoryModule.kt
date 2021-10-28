package io.arunbuilds.rickandmorty.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.arunbuilds.rickandmorty.network.repository.characters.CharactersRepository
import io.arunbuilds.rickandmorty.network.repository.characters.CharactersRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsCharactersRepository(
        CharactersRepositoryImpl: CharactersRepositoryImpl
    ): CharactersRepository
}