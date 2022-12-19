package com.example.movieapp

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import androidx.room.Room
import com.example.movieapp.data.Search
import com.example.movieapp.data.favorites.FavoriteMovie
import com.example.movieapp.data.favorites.FavoriteMovieDatabase
import com.example.movieapp.data.favorites.FavoriteMovieRepository

import com.example.movieapp.remote.MovieInterface
import com.example.movieapp.ui.search.MoviePaging
import com.example.movieapp.ui.search.MoviePagingAdapter

import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val movieInterface: MovieInterface
    ) : ViewModel() {

    private val repository = FavoriteMovieRepository(
        Room.databaseBuilder(
            appContext,
            FavoriteMovieDatabase::class.java,
            "movie_db"
        ).build().getFavoriteMovieDao()
    )
    private val query = MutableLiveData<String>()

    val list = query.switchMap { query ->
        Pager(PagingConfig(pageSize = 10)) {
            MoviePaging(appContext, query, movieInterface)
        }.liveData.cachedIn(viewModelScope)
    }


    fun setQuery(s: String) {
        query.postValue(s)
    }



    fun addToFavorite(movie: Search) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addToFavorite(
                FavoriteMovie(
                    movie.imdbID,
                    movie.Title,
                    movie.Poster,
                    movie.Type,
                    movie.Year
                )

            )
        }
    }
    suspend fun checkMovie(id:String) = repository.checkMovie(id)


    fun removeFromFavorite(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFromFavorite(id)
        }
    }
}

