package com.example.movieapp.data.favorites

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteMovieDao {
    @Insert
    fun addToFavorite(favoriteMovie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movie")
    fun getFavoriteMovie(): LiveData<List<FavoriteMovie>>

    @Query("SELECT count(*) FROM favorite_movie WHERE favorite_movie.imdbID = :id")
    fun checkMovie(id: String): Int

    @Query("DELETE FROM favorite_movie WHERE favorite_movie.imdbID = :id" )
    fun removeFromFavorite(id: String) : Int

}