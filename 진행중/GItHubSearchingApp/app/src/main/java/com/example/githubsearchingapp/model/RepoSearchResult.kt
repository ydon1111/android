package com.example.githubsearchingapp.model

import java.lang.Exception

sealed class RepoSearchResult{
    data class Success(val data:List<Repo>): RepoSearchResult()
    data class Error(val error : Exception) : RepoSearchResult()
}