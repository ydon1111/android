package com.yeongdon.composemovieapp.data.remote.model

data class Search(
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String,
    var isFavourite: Boolean? = false
)


