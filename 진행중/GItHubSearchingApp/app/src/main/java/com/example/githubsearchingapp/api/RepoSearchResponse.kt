package com.example.githubsearchingapp.api

import com.example.githubsearchingapp.model.Repo
import com.google.gson.annotations.SerializedName


data class RepoSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<Repo> = emptyList(),
    val nextPage: Int? = null
)