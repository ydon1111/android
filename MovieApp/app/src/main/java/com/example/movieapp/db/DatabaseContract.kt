package com.example.movieapp.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favoritemovie"
            const val _ID = "id"
            const val TITLE = "originalTitle"
            const val POSTER = "poster"
            const val TYPE= "type"
            const val YEAR = "year"
        }
    }
}