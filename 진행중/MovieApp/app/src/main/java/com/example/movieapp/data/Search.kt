package com.example.movieapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Search(
    var imdbID : String,
    val Title : String,
    val Poster : String,
    val Type : String,
    val Year : String,
): java.io.Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    val BASE_URL get() = "https://www.omdbapi.com"
}
