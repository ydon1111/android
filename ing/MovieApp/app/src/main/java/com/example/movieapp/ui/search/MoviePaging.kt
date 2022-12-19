package com.example.movieapp.ui.search


import android.content.Context
import android.util.Log
import android.view.View

import androidx.paging.PagingSource
import androidx.paging.PagingState

import com.example.movieapp.R
import com.example.movieapp.data.Search

import com.example.movieapp.remote.MovieInterface
import com.example.movieapp.utils.Constants

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


import javax.inject.Inject

class MoviePaging @Inject constructor(
    val appContext: Context,
    val s: String,
    val movieInterface: MovieInterface,
) : PagingSource<Int, Search>() {

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state?.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        val page = params.key ?: 1

        return try {
            val data = movieInterface.getAllMovies(s, page, Constants.API_KEY)

            // Return value
            LoadResult.Page(
                data = data.body()?.Search!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.body()?.Search?.isEmpty()!!) null else page + 1
            )

        } catch (e: Exception) {

//            Log.d("MovieData", "영화 데이터 찾을 수 없음")
            val toast = Toast.makeText(appContext, R.string.nothing_to_display, Toast.LENGTH_LONG)
            toast.show()

            // Return value
            LoadResult.Error(e)
        }
    }

}