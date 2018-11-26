package com.jurgielewicz.simpleweatherapp.utilities

import android.app.FragmentManager
import android.support.v4.view.GravityCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.jurgielewicz.simpleweatherapp.R
import com.jurgielewicz.simpleweatherapp.R.id.*
import com.jurgielewicz.simpleweatherapp.adapters.ViewPagerAdapter
import com.jurgielewicz.simpleweatherapp.fragments.CurrentWeatherFragment
import com.jurgielewicz.simpleweatherapp.fragments.DailyWeatherFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*


fun timestampConverter(num: Long, i: Int): String {
    // i = 0 - date
    // i = 1 - day
    // i== 2 - hour
    var format = "EEE"
    when(i){
        0 -> format = "dd/MM"
        1 -> format = "EEE"
        2 -> format = "HH:mm"
    }
    val formatter = SimpleDateFormat(format)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = num * 1000
    return formatter.format(calendar.time)
}





