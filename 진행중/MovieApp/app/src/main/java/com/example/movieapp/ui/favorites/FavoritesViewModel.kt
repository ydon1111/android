package com.example.movieapp.ui.favorites



import androidx.lifecycle.ViewModel
import com.example.movieapp.data.favorites.FavoriteMovieRepository
import javax.inject.Inject


class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteMovieRepository
) : ViewModel(){
    val movies = repository.getFavoriteMovies()
}