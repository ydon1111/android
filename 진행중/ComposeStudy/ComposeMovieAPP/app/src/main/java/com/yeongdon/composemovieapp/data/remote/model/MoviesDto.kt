package com.yeongdon.composemovieapp.data.remote.model

data class MoviesDto(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)