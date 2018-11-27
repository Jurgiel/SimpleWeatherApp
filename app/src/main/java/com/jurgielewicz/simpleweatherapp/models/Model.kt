package com.jurgielewicz.simpleweatherapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Periods(val timestamp: Long,
                   val validTime: String,
                   val dateTimeISO: String,
                   val maxTempC: Int,
                   val minTempC: Int,
                   val humidity: Int,
                   val tempC: Int?,
                   val pop: Double?,
                   val feelsLikeC: Int,
                   val weather: String,
                   val weatherPrimary: String,
                   val icon: String,
                   val windSpeedKPH: Int) : Parcelable
@Parcelize
data class Response(val periods: List<Periods>) : Parcelable

@Parcelize
data class Result(val response: List<Response>) : Parcelable
