package com.example.movieapp.hilt

import android.content.Context
import androidx.room.Room
//import com.example.movieapp.data.favorites.FavoriteMovieDatabase
import com.example.movieapp.remote.MovieInterface

import com.example.movieapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HiltModules {

    @Singleton
    @Provides
    fun provideRetrofitInterface(): MovieInterface {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(MovieInterface::class.java)
    }


//    @Provides
//    @Singleton
//    fun provideFavMovieDatabase(
//        @ApplicationContext app:Context
//    )=Room.databaseBuilder(
//        app,
//        FavoriteMovieDatabase::class.java,
//        "movie_db"
//    ).build()
//
//    @Provides
//    @Singleton
//    fun provideFavMovieDao(db: FavoriteMovieDatabase) = db.getFavoriteMovieDao()


}
