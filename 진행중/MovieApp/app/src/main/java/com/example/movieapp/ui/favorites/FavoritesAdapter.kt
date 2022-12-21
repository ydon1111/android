package com.example.movieapp.ui.favorites

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movieapp.R
import com.example.movieapp.data.favorites.FavoriteMovie
import com.example.movieapp.databinding.HolderMovieBinding


class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    private lateinit var list: List<FavoriteMovie>

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setMovieList(list: List<FavoriteMovie>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: HolderMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteMovie: FavoriteMovie) {
            with(binding) {
                Glide.with(itemView)
                    .load(favoriteMovie.Poster)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(movieImage)
                movieTitle.text = favoriteMovie.Title
                movieYear.text = favoriteMovie.Year
                movieType.text = favoriteMovie.Type
                binding.root.setOnClickListener { onItemClickCallback?.onItemClick(favoriteMovie) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = HolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        Log.e("adapter", "bind view holder")
        holder.bind(list[position])
    }


    interface OnItemClickCallback {
        fun onItemClick(favoriteMovie: FavoriteMovie)
    }
}