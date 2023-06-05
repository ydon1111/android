package com.yeongdon.composemovieapp.presentation.movie_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeongdon.composemovieapp.common.Constants
import com.yeongdon.composemovieapp.common.Resource
import com.yeongdon.composemovieapp.data.remote.model.Search
import com.yeongdon.composemovieapp.domain.repository.OmdbRepository
import com.yeongdon.composemovieapp.domain.use_case.get_movies.GetMoviesUseCase
import com.yeongdon.composemovieapp.presentation.MovieScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val repository: OmdbRepository
) : ViewModel() {

    private val _state = mutableStateOf(MovieListState())
    val state: State<MovieListState> = _state

    var textData: String = ""

    fun getMovies(page: Int, apiKey: String) {
        getMoviesUseCase(textData, page, apiKey).onEach { result ->
            Log.d("result@@", "$result")
            when (result) {
                is Resource.Success -> {
                    _state.value = MovieListState(movies = result.data?.Search ?: emptyList())
                    Log.d("_state.value@@", "${_state.value}")
                    Log.d("SearchTest", "$_state")
                }
                is Resource.Error -> {
                    _state.value = MovieListState(error = result.message ?: "예기치 않은 오류 발생")
                }
                is Resource.Loading -> {
                    _state.value = MovieListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}