package com.jurgielewicz.simpleweatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jurgielewicz.simpleweatherapp.models.Result

class DetailedWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_weather)

        val details = intent.getParcelableExtra<Result>("detailed_weather")
        val position = intent.getIntExtra("position", 0)

        Log.d("DetailedActivity", details.response[0].periods[position].weather)
    }
}
