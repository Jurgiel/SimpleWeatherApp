package com.jurgielewicz.simpleweatherapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jurgielewicz.simpleweatherapp.R
import com.jurgielewicz.simpleweatherapp.adapters.DailyWeatherAdapter
import com.jurgielewicz.simpleweatherapp.models.Response
import kotlinx.android.synthetic.main.fragment_current_weather.view.*
import kotlinx.android.synthetic.main.fragment_daily_weather.view.*


class DailyWeatherFragment : Fragment() {
    var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_daily_weather, container, false)
        rootView!!.dailyRecycler.layoutManager = LinearLayoutManager(activity)
        rootView!!.dailyRecycler.adapter = null
        return rootView
    }

    fun updateRecView(v: List<Response>){
        rootView?.dailyRecycler?.adapter = DailyWeatherAdapter(v)
    }

}
