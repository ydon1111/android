package com.yeongdon.composemovieapp.presentation.movie_detail

import com.yeongdon.composemovieapp.data.remote.model.MovieDto

data class MovieDetailState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val movie: MovieDto?=null,
    val error: String = ""

)