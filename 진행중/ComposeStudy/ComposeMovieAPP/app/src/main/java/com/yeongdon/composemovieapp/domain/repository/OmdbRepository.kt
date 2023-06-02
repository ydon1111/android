package com.yeongdon.composemovieapp.domain.repository

import com.yeongdon.composemovieapp.data.remote.model.MovieDto
import com.yeongdon.composemovieapp.data.remote.model.MoviesDto

interface OmdbRepository {

    suspend fun getMovies(name: String, page: Int, apiKey: String): MoviesDto
    suspend fun getMovie(id: String, apiKey: String): MovieDto


}