package com.example.favoriteplacesapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.favoriteplacesapp.R
import com.example.favoriteplacesapp.databinding.ItemFavoritePlaceBinding

import com.example.favoriteplacesapp.models.FavoritePlaceModel

open class FavoritePlacesAdapter(
    private val context: Context,
    private var list: ArrayList<FavoritePlaceModel>
) : RecyclerView.Adapter<FavoritePlacesAdapter.FavoritePlaceViewHolder>() {

    private var onClickListener: OnClickListener? = null

    class FavoritePlaceViewHolder(val binding: ItemFavoritePlaceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):
            FavoritePlaceViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_favorite_place, viewGroup, false)

        return FavoritePlaceViewHolder(ItemFavoritePlaceBinding.bind(view))
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: FavoritePlaceViewHolder, position: Int) {
        val model = list[position]
            holder.binding.ivPlaceImage.setImageURI(Uri.parse(model.image))
            holder.binding.tvTitle.text = model.title
            holder.binding.tvDescription.text = model.description

            holder.itemView.setOnClickListener {
                if(onClickListener != null){
                    onClickListener!!.onClick(position,model)
                }
            }
    }

    override fun getItemCount() = list.size

    interface OnClickListener{
        fun onClick(position: Int, model: FavoritePlaceModel)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener

    }
}




