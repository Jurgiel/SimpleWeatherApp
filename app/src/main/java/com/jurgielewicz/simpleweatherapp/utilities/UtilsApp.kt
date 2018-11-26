package com.jurgielewicz.simpleweatherapp.utilities

import android.support.v4.view.GravityCompat
import com.jurgielewicz.simpleweatherapp.R.id.*
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




