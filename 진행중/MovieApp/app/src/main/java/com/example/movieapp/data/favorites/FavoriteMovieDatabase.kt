package com.example.movieapp.data.favorites

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [FavoriteMovie::class],
    version = 1
)
abstract class FavoriteMovieDatabase : RoomDatabase(){
    abstract fun getFavoriteMovieDao(): FavoriteMovieDao
}