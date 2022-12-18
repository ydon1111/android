package com.example.movieapp

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData

import com.example.movieapp.remote.MovieInterface
import com.example.movieapp.ui.search.MoviePaging
import com.example.movieapp.ui.search.MoviePagingAdapter

import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val movieInterface: MovieInterface,
    ) : ViewModel() {
    

    private val query = MutableLiveData<String>()



    val list = query.switchMap { query ->
        Pager(PagingConfig(pageSize = 10)) {
            MoviePaging(appContext, query, movieInterface)
        }.liveData.cachedIn(viewModelScope)
    }




    fun setQuery(s: String) {
        query.postValue(s)
    }


}

