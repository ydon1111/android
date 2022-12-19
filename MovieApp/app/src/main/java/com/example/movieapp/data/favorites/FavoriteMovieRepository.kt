package com.example.movieapp.data.favorites

import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) {
    fun addToFavorite(favoriteMovie: FavoriteMovie) =
        favoriteMovieDao.addToFavorite(favoriteMovie)
    fun getFavoriteMovies() = favoriteMovieDao.getFavoriteMovie()
    fun checkMovie(id: String) = favoriteMovieDao.checkMovie(id)
    fun removeFromFavorite(id: String) {
        favoriteMovieDao.removeFromFavorite(id)
    }

}