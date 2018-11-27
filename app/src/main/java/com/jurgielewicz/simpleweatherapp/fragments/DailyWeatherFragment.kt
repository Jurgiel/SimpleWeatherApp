package com.jurgielewicz.simpleweatherapp.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jurgielewicz.simpleweatherapp.DetailedWeatherActivity
import com.jurgielewicz.simpleweatherapp.MainActivity

import com.jurgielewicz.simpleweatherapp.R
import com.jurgielewicz.simpleweatherapp.adapters.DailyWeatherAdapter
import com.jurgielewicz.simpleweatherapp.models.Response
import com.jurgielewicz.simpleweatherapp.models.Result
import com.jurgielewicz.simpleweatherapp.utilities.OnItemClickListener
import com.jurgielewicz.simpleweatherapp.utilities.addOnItemClickListener
import kotlinx.android.synthetic.main.fragment_current_weather.view.*
import kotlinx.android.synthetic.main.fragment_daily_weather.view.*


class DailyWeatherFragment : Fragment() {
   private var rootView: View? = null
    private var data: List<Response>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_daily_weather, container, false)
        rootView!!.dailyRecycler.layoutManager = LinearLayoutManager(activity)
        rootView!!.dailyRecycler.adapter = null
        rootView!!.dailyRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val intent = Intent(context, DetailedWeatherActivity::class.java)
                intent.putExtra("detailed_weather", Result(data!!))
                startActivity(intent)

            }
        })
        return rootView
    }

    fun updateRecView(v: List<Response>){
        data = v
        rootView?.dailyRecycler?.adapter = DailyWeatherAdapter(v)
    }

}
