package com.jurgielewicz.simpleweatherapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.jurgielewicz.simpleweatherapp.MainActivity
import com.jurgielewicz.simpleweatherapp.R
import com.jurgielewicz.simpleweatherapp.adapters.HourlyWeatherAdapter
import com.jurgielewicz.simpleweatherapp.models.Periods
import kotlinx.android.synthetic.main.fragment_current_weather.view.*


class CurrentWeatherFragment : Fragment() {
    var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_current_weather, container, false)
        rootView!!.hourlyRecycler.layoutManager = LinearLayoutManager(activity)
        rootView!!.hourlyRecycler.adapter = null

        rootView!!.saveLocationButton.setOnClickListener {view ->
            Log.d("CurrentWeatherFragment", "Location button click")
        }

        return rootView
    }

    fun updateRecView(v: List<Periods>){
        rootView?.hourlyRecycler?.adapter = HourlyWeatherAdapter(v)
    }
}
