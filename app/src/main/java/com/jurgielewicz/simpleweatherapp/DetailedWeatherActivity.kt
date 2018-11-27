package com.jurgielewicz.simpleweatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jurgielewicz.simpleweatherapp.models.Response
import com.jurgielewicz.simpleweatherapp.models.Result
import com.jurgielewicz.simpleweatherapp.utilities.downloadImage
import com.jurgielewicz.simpleweatherapp.utilities.getIconUrl
import com.jurgielewicz.simpleweatherapp.utilities.timestampConverter
import kotlinx.android.synthetic.main.activity_detailed_weather.*

class DetailedWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_weather)

        val details = intent.getParcelableExtra<Response>("detailed_weather")
        val position = intent.getIntExtra("position", 0)

        Log.d("DetailedActivity", details.periods[position].weather)
        val data = details.periods[position]

        dateTextView_DetailedActivity.text = timestampConverter(data.timestamp, 3)
        maxTempTextView_DetailedActivity.text = data.maxTempC.toString().plus("℃\t")
        feelsLikeTextView_DetailedActivity.text = data.feelsLikeC.toString().plus("℃\t")
        humidityTextView_DetailedActivity.text = data.humidity.toString().plus("%")
        popTextView_DetailedActivity.text = data.pop.toString().plus("%")
        minTempTextView_DetailedActivity.text = data.minTempC.toString().plus("℃\t")
        windspeedTextView_DetailedActivity.text = data.windSpeedKPH.toString().plus(" km/h")
        sunriseTextView_DetailedActivity.text= timestampConverter(data.sunrise, 2)
        weatherTextView_DetailedActivity.text = data.weather
        sunsetTextView_DetailedActivity.text = timestampConverter(data.sunset, 2)
        downloadImage(getIconUrl(data.icon), icon_DetailedActivity, 250, 250)
    }
}
