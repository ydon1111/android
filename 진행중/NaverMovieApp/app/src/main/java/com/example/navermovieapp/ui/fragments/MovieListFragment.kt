package com.example.navermovieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.ExperimentalPagingApi
import com.example.navermovieapp.R
import com.example.navermovieapp.databinding.FragmentMovieListBinding
import com.example.navermovieapp.model.Keyword
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class MovieListFragment : Fragment(R.layout.fragment_movie_list)
{

    private var _binding: FragmentMovieListBinding? = null
    val binding get() = _binding!!

    var list = ArrayList<Keyword>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

}