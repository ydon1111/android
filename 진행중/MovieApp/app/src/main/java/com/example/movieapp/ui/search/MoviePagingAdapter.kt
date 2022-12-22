package com.example.movieapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.MovieViewModel
import com.example.movieapp.data.Search
import com.example.movieapp.databinding.HolderMovieBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


//LiveData 연결을 위한 PagingDataAdapter 사용
//private val viewModel: MovieViewModel
class MoviePagingAdapter @Inject constructor(private val viewModel: MovieViewModel) :
    PagingDataAdapter<Search, MoviePagingAdapter.MyViewHolder>(DIFF_UTIL) {

//    var onCLick: ((String) -> Unit)? = null
//
//    fun onMovieClick(listener: (String) -> Unit) {
//        onCLick = listener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // 즐겨찾기 추가시 데이터 저장 구현
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = getItem(position)

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkMovie(data?.imdbID!!)
            withContext(Dispatchers.Main) {
                if (count > 0) {
                    holder.heartCheck.isChecked = true
                    _isChecked = true
                } else {
                    holder.heartCheck.isChecked = false
                    _isChecked = false
                }
            }
        }

        holder.typeCheck.text = data?.Type!!
        holder.titleCheck.text = data.Title

        holder.heartCheck.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.addToFavorite(data)
            } else {
                viewModel.removeFromFavorite(data.imdbID)
            }
            holder.heartCheck.isChecked = _isChecked
        }
    }

    // HolderMovie binding
    inner class MyViewHolder(val viewDataBinding: HolderMovieBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener {

        //hearBox 클릭 구현
        val heartCheck: ToggleButton = viewDataBinding.checkFavorite
        val titleCheck: TextView = viewDataBinding.movieTitle
        val typeCheck: TextView = viewDataBinding.movieType

        init {
            heartCheck.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (heartCheck.isChecked) {
                println("하트 클릭@@@@")
            } else {
                println("하트 지움@@@@")
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Search>() {
            override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem.Poster == newItem.Poster
            }

            override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem == newItem
            }
        }
    }
}



