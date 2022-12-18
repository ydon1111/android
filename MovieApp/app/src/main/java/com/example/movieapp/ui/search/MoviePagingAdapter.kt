package com.example.movieapp.ui.search


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.movieapp.BR
import com.example.movieapp.R
import com.example.movieapp.data.Search
import com.example.movieapp.databinding.HolderMovieBinding
import java.util.Objects


import javax.inject.Inject


//LiveData 연결을 위한 PagingDataAdapter 사용
class MoviePagingAdapter() :
    PagingDataAdapter<Search, MoviePagingAdapter.MyViewHolder>(DIFF_UTIL) {

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

    // HolderMovie binding
    inner class MyViewHolder(val viewDataBinding: HolderMovieBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root),View.OnClickListener {

        val favoriteList = arrayListOf<Unit>()

        //hearBox 클릭 구현
        val heartCheck: CheckBox = viewDataBinding.checkFavorite

        init {
            heartCheck.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (heartCheck.isChecked){
                println("하트 클릭@@@@")
            }
            else{
                println("하트 지움@@@@")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.viewDataBinding.setVariable(BR.movie, data)

        holder.viewDataBinding.root.setOnClickListener {
            Log.d("MovieClick2", "영화 눌림###")
            // TODO: onClick action 추가(popup menu), 싱글톤 , Static 변수를 영화정보를 담는다. 리스트?

        }
    }


}


