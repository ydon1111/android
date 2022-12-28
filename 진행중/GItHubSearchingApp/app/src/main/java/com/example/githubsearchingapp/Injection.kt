package com.example.githubsearchingapp

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.githubsearchingapp.api.GithubService
import com.example.githubsearchingapp.data.GithubRepository
import com.example.githubsearchingapp.ui.ViewModelFactory

object Injection {

    private fun provideGithubRepository():GithubRepository{
        return GithubRepository(GithubService.create())
    }

    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory{
        return ViewModelFactory(owner, provideGithubRepository())
    }
}