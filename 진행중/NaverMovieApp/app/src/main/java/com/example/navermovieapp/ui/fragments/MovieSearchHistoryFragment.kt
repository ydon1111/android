package com.example.navermovieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.ExperimentalPagingApi
import com.example.navermovieapp.R
import com.example.navermovieapp.databinding.FragmentMovieSearchHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class MovieSearchHistoryFragment : Fragment(R.layout.fragment_movie_search_history) {
    private var _binding: FragmentMovieSearchHistoryBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieSearchHistoryBinding.inflate(inflater,container,false)


        return binding.root
    }
}