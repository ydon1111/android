package com.yeongdon.composemovieapp.presentation.movie_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeongdon.composemovieapp.common.Constants
import com.yeongdon.composemovieapp.common.Resource
import com.yeongdon.composemovieapp.domain.repository.OmdbRepository
import com.yeongdon.composemovieapp.domain.use_case.get_movie.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel@Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val repository: OmdbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _state = mutableStateOf(MovieDetailState())
    val stateApi: State<MovieDetailState> = _state

    init {
        savedStateHandle.get<String>("imdbID")?.let { imdbID ->
            getMovie(false,imdbID, Constants.URL_API_KEY)
        }
    }


    private fun getMovie(isRefreshing: Boolean = false, id:String,apiKey:String){
        getMovieUseCase(id,apiKey).onEach { result ->
            when(result){
                is Resource.Success ->{
                    _state.value =MovieDetailState(movie = result.data)
                }
                is Resource.Error ->{
                    _state.value =MovieDetailState(error = result.message ?: "예기치 않은 오류 발생")
                }
                is Resource.Loading ->{
                    if (isRefreshing) {
                        _state.value = MovieDetailState(isRefreshing = true)
                    } else {
                        _state.value = MovieDetailState(isLoading = true)
                    }
                }
            }

        }.launchIn(viewModelScope)
    }
}