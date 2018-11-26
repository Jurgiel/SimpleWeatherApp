package com.jurgielewicz.simpleweatherapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.jurgielewicz.simpleweatherapp.R
import com.jurgielewicz.simpleweatherapp.adapters.DailyWeatherAdapter
import kotlinx.android.synthetic.main.fragment_current_weather.view.*
import com.jurgielewicz.simpleweatherapp.models.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CurrentWeatherFragment : Fragment() {
    var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_current_weather, container, false)
        rootView!!.hourlyRecycler.layoutManager = LinearLayoutManager(activity)
        rootView!!.hourlyRecycler.adapter = null

        return rootView
    }

    fun updateRecView(v: List<Response>){
        rootView?.hourlyRecycler?.adapter = DailyWeatherAdapter(v)
    }

}
