package com.example.githubsearchingapp.ui

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.room.PrimaryKey
import androidx.savedstate.SavedStateRegistryOwner
import com.example.githubsearchingapp.data.GithubRepository

class ViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: GithubRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(SearchRepositoriesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SearchRepositoriesViewModel(repository,handle) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}