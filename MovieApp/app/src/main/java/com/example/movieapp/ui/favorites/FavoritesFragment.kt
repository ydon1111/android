package com.example.movieapp.ui.favorites

import android.graphics.Movie
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.MovieViewModel
import com.example.movieapp.R
import com.example.movieapp.data.Search
import com.example.movieapp.data.favorites.FavoriteMovie
import com.example.movieapp.databinding.FragmentFavoritesBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        val binding =  FragmentFavoritesBinding.bind(view)
        val viewModel by viewModels<FavoritesViewModel>()
        val adapter = FavoritesAdapter()


        viewModel.movies.observe(viewLifecycleOwner){
            adapter.setMovieList(it)
            binding.apply {
                rvFavoriteMovie.setHasFixedSize(true)
                rvFavoriteMovie.adapter = adapter
            }
        }

        adapter.setOnItemClickCallback(object : FavoritesAdapter.OnItemClickCallback{
            override fun onItemClick(favoriteMovie: FavoriteMovie) {
                val movie = Search(
                    favoriteMovie.imdbID,
                    favoriteMovie.Title,
                    favoriteMovie.Poster,
                    favoriteMovie.Year,
                    favoriteMovie.Type
                )
//                val action = .actionFavoritesFragmentNavToSearchFragmentNav(movie)
                findNavController()
            }

        })
    }
}