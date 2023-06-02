package com.yeongdon.composemovieapp.presentation.movie_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeongdon.composemovieapp.common.Constants
import com.yeongdon.composemovieapp.common.Resource
import com.yeongdon.composemovieapp.domain.repository.OmdbRepository
import com.yeongdon.composemovieapp.domain.use_case.get_movies.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val repository: OmdbRepository
) : ViewModel() {

    private val _state = mutableStateOf(MovieListState())
    val stateApi: State<MovieListState> = _state



    init {
        getMovies("iron",2,Constants.URL_API_KEY )
    }

    fun getMovies(name:String, page:Int,apiKey:String){
        getMoviesUseCase(name,page,apiKey).onEach { result ->
            when(result){
                is Resource.Success ->{
                    _state.value = MovieListState(movies = result.data?.Search ?: emptyList())
                }
                is Resource.Error ->{
                    _state.value = MovieListState(error = result.message ?: "예기치 않은 오류 발생")
                }
                is Resource.Loading ->{
                    _state.value = MovieListState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }


}