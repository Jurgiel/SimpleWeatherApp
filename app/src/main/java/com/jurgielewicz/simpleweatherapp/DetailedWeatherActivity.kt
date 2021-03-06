package com.jurgielewicz.simpleweatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jurgielewicz.simpleweatherapp.models.Response
import com.jurgielewicz.simpleweatherapp.utilities.downloadImage
import com.jurgielewicz.simpleweatherapp.utilities.getIconUrl
import com.jurgielewicz.simpleweatherapp.utilities.timeConverter
import kotlinx.android.synthetic.main.activity_detailed_weather.*

class DetailedWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_weather)

        val position = intent.getIntExtra("position", 0)
        val data = intent.getParcelableExtra<Response>("detailed_weather").periods?.get(position)

        dateTextView_DetailedActivity.text = timeConverter(data?.dateTimeISO, 3)
        maxTempTextView_DetailedActivity.text = "MAX: " + data?.maxTempC.toString() + ("℃\t")
        averageTempTextView_DetailedActivity.text = "AVERAGE: " + data?.avgTempC.toString().plus("℃\t")
        humidityTextView_DetailedActivity.text = "HUMIDITY: " + data?.humidity.toString().plus("%")
        popTextView_DetailedActivity.text = "PRECIPTATION: " + data?.pop.toString().plus("%")
        minTempTextView_DetailedActivity.text = "MIN: " + data?.minTempC.toString().plus("℃\t")
        windspeedTextView_DetailedActivity.text = "WIND SPEED: " + data?.windSpeedKPH.toString().plus(" km/h")
        weatherTextView_DetailedActivity.text = data?.weather
        sunriseTextView_DetailedActivity.text = "SUNRISE: " + timeConverter(data?.sunriseISO, 2)
        sunsetTextView_DetailedActivity.text = "SUNSET: " + timeConverter(data?.sunsetISO, 2)
        downloadImage(getIconUrl(data?.icon), icon_DetailedActivity, 250, 250)
    }
}
