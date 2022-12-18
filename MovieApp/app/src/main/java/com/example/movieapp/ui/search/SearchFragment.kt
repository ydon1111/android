package com.example.movieapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.MovieViewModel
import com.example.movieapp.databinding.FragmentSearchBinding


import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment @Inject constructor()  : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    val viewModel: MovieViewModel by viewModels()
    private val movieAdapter = MoviePagingAdapter()

    //뷰 바인딩
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //  액티비티는 layoutInflater 를 쓰고 fragment 는 inflater 를 사용
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Load Search text
        binding.movieSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.setQuery(it)
                }
                // 키보드 숨기기 위해 포커스 제거
                binding.movieSearch.clearFocus()
                setRecyclerView()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                Log.d("검색창_조건없음", "검색어 찾을 수 없음")
                setNoResultView()
                return false
            }
        })



        viewModel.list.observe(viewLifecycleOwner) {
            movieAdapter.submitData(lifecycle, it)
        }
    }



    //검색 결과 없음 화면 출력
    private fun setRecyclerView() {
        binding.movieRecycler.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }.visibility = View.VISIBLE

        binding.noResultData.visibility = View.GONE
    }

    fun setNoResultView() {
        binding.movieRecycler.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }.visibility = View.GONE

        binding.noResultData.visibility = View.VISIBLE
    }



}





