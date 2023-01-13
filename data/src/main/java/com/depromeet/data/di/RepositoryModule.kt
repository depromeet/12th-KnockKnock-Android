package com.depromeet.data.di

import com.depromeet.data.api.MainAPIService
import com.depromeet.data.repository.MainRepositoryImpl
import com.depromeet.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMainRepository(mainAPIService: MainAPIService): MainRepository {
        return MainRepositoryImpl(mainAPIService)
    }
}