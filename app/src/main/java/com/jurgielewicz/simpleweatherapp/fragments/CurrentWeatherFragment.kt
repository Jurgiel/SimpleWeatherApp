package com.jurgielewicz.simpleweatherapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.jurgielewicz.simpleweatherapp.MainActivity
import com.jurgielewicz.simpleweatherapp.R
import com.jurgielewicz.simpleweatherapp.adapters.HourlyWeatherAdapter
import com.jurgielewicz.simpleweatherapp.database.DatabaseHelper
import com.jurgielewicz.simpleweatherapp.models.Periods
import com.jurgielewicz.simpleweatherapp.models.Places
import com.jurgielewicz.simpleweatherapp.utilities.downloadImage
import com.jurgielewicz.simpleweatherapp.utilities.getIconUrl
import com.jurgielewicz.simpleweatherapp.utilities.timeConverter
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.android.synthetic.main.fragment_current_weather.view.*


class CurrentWeatherFragment : Fragment() {
    var rootView: View? = null
    private lateinit var place: Places
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_current_weather, container, false)
        rootView?.hourlyRecycler.layoutManager = LinearLayoutManager(activity)
        rootView!!.hourlyRecycler.adapter = null
        val db = DatabaseHelper(context = activity)
        rootView!!.saveLocationButton.setOnClickListener {view ->
            Log.d("CurrentWeatherFragment", "Location button click")
            val db = DatabaseHelper(context = activity)
            if(db.existsCheck(place)) {
                db.deletePlace(place)
                rootView!!.saveLocationButton.setImageResource(R.drawable.ic_not_saved)
            } else {
                db.addPlace(place)
                rootView!!.saveLocationButton.setImageResource(R.drawable.ic_saved)
            }
        }
        return rootView
    }

    fun updateRecView(v: List<Periods>, p: Places){
        val db = DatabaseHelper(activity).existsCheck(p)
        if(db) rootView!!.saveLocationButton.setImageResource(R.drawable.ic_saved)
        else rootView!!.saveLocationButton.setImageResource(R.drawable.ic_not_saved)
        place = p
        rootView?.hourlyRecycler?.adapter = HourlyWeatherAdapter(v)

        cityTextView_CurrentLayout.text = p.name
        weatherTextView_CurrentLayout.text = v[0].weather
        temperatureTextView_CurrentLayout.text = v[0].tempC.toString().plus("°C")
        feelsLikeTextView_CurrentLayout.text = "Feels like " + v[0].tempC.toString().plus("°C")
        downloadImage(getIconUrl(v[0].icon), icon_CurrentLayout, 300, 300)
        dateTextView_CurentLayout.text = timeConverter(v[0].dateTimeISO, 3)
    }
}
