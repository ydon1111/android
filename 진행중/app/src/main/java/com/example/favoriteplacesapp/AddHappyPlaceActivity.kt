package com.example.favoriteplacesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.favoriteplacesapp.databinding.ActivityAddHappyPlaceBinding



class AddHappyPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddHappyPlaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddHappyPlaceBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAddPlace)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarAddPlace.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}