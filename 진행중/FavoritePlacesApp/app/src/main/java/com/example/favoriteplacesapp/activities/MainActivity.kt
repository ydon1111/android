package com.example.favoriteplacesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favoriteplacesapp.adapters.FavoritePlacesAdapter
import com.example.favoriteplacesapp.database.DatabaseHandler
import com.example.favoriteplacesapp.databinding.ActivityMainBinding
import com.example.favoriteplacesapp.models.FavoritePlaceModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAddHappyPlace.setOnClickListener {
            val intent = Intent(this, AddHappyPlaceActivity::class.java)
            startActivity(intent)
        }
        getFavoritePlacesListFromLocalDB()
    }

    private fun getFavoritePlacesListFromLocalDB() {
        val dbHandler = DatabaseHandler(this)
        val getFavoritePlaceList: ArrayList<FavoritePlaceModel> = dbHandler.getFavoritePlacesList()

        if (getFavoritePlaceList.size > 0) {
            binding.rvFavoritePlacesList.visibility = View.VISIBLE
            binding.tvNoRecordsAvailable.visibility = View.GONE
            setupFavoritePlacesRecyclerView(getFavoritePlaceList)

        } else {
            binding.rvFavoritePlacesList.visibility = View.GONE
            binding.tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }

    private fun setupFavoritePlacesRecyclerView(
        favoritePlaceList: ArrayList<FavoritePlaceModel>
    ) {
        binding.rvFavoritePlacesList.layoutManager =
            LinearLayoutManager(this)

        binding.rvFavoritePlacesList.setHasFixedSize(true)

        val placesAdapter = FavoritePlacesAdapter(this, favoritePlaceList)
        binding.rvFavoritePlacesList.adapter = placesAdapter

    }

}