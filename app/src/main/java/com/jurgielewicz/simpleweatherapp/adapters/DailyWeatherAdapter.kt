package com.jurgielewicz.simpleweatherapp.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jurgielewicz.simpleweatherapp.R
import com.jurgielewicz.simpleweatherapp.models.Periods
import com.jurgielewicz.simpleweatherapp.models.Response
import com.jurgielewicz.simpleweatherapp.models.ViewHolder
import com.jurgielewicz.simpleweatherapp.utilities.downloadImage
import com.jurgielewicz.simpleweatherapp.utilities.getIconUrl
import com.jurgielewicz.simpleweatherapp.utilities.timestampConverter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_daily_weather.view.*
import kotlinx.android.synthetic.main.row_hourly_weather.view.*

class DailyWeatherAdapter(val periods:List<Periods>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val weatherRow = layoutInflater.inflate(R.layout.row_daily_weather, parent, false)

        return ViewHolder(weatherRow)
    }

    override fun getItemCount(): Int {
        return periods.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.weatherTextView_daily_weather_row.text = periods[position].weather
        holder.itemView.day_daily_weather_row.text = timestampConverter(periods[position].timestamp, 1)
        holder.itemView.dateTextView_Daily_Weather_Row.text = timestampConverter(periods[position].timestamp, 0)
        holder.itemView.maxTempTextView_daily_weather_row.text = periods[position].maxTempC.toString().plus("℃\t")
        holder.itemView.minTempTextView_daily_weather_row.text = periods[position].minTempC.toString().plus("℃\t")

        downloadImage(getIconUrl(periods[position].icon), holder.itemView.icon_daily_weather_row, 200, 200)

    }

}