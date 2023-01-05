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
                var inputTitle = etMovieSearch.text.toString()
                inputTitle = item.keyword
                btnSearch.setOnClickListener {
                    searchedWord = binding.etMovieSearch.toString()
                    Log.i("검색어확인","검색어 : $searchedWord")
                    Log.e("검색눌림","검색눌림 확인@@@@")
                    TODO("검색 클릭시 검색어 저장 및 query 전송 구현")

                }


                btnRecent.setOnClickListener {


                }






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
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}