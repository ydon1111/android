package com.example.navermovieapp.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.navermovieapp.databinding.FragmentMovieListBinding
import com.example.navermovieapp.model.Keyword

class SearchKeywordAdapter: ListAdapter<Keyword,SearchKeywordAdapter.SearchViewHolder>(diffUtil){

    private lateinit var searchedWord: String
    inner class SearchViewHolder(val binding: FragmentMovieListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Keyword){
            binding.apply {
                searchedWord = binding.etMovieSearch.text.toString()
                searchedWord = item.keyword

//                btnSearch.setOnClickListener {
//                    searchedWord = binding.etMovieSearch.toString()
//                }
//                btnRecent.setOnClickListener {
//                }

            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Keyword>() {
            override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
            TODO()
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}