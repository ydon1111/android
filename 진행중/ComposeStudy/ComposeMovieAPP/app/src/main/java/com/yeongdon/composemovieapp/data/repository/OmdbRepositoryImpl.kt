package com.yeongdon.composemovieapp.data.repository

import com.yeongdon.composemovieapp.data.remote.api.OmdbApi
import com.yeongdon.composemovieapp.data.remote.model.MovieDto
import com.yeongdon.composemovieapp.data.remote.model.MoviesDto
import com.yeongdon.composemovieapp.domain.repository.OmdbRepository
import javax.inject.Inject

class OmdbRepositoryImpl @Inject constructor(
    private val api: OmdbApi
) : OmdbRepository{


    override suspend fun getMovies(name: String, page: Int, apiKey: String): MoviesDto {
        return api.searchForMovies(movie_name = name, page = page, api_key = apiKey)
    }

    override suspend fun getMovie(id: String, apiKey: String): MovieDto {
        return api.getMovieById(imdbID = id, api_key = apiKey)
    }
}