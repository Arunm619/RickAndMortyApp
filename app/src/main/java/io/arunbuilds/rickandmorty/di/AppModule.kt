package io.arunbuilds.rickandmorty.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.arunbuilds.rickandmorty.util.AppSchedulerProvider
import io.arunbuilds.rickandmorty.util.SchedulerProvider

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

}

