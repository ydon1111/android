package com.yeongdon.composemovieapp.di

import com.yeongdon.composemovieapp.common.Constants
import com.yeongdon.composemovieapp.data.remote.api.OmdbApi
import com.yeongdon.composemovieapp.data.repository.OmdbRepositoryImpl
import com.yeongdon.composemovieapp.domain.repository.OmdbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOmdbApi(): OmdbApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: OmdbApi): OmdbRepository {
        return OmdbRepositoryImpl(api)
    }

}