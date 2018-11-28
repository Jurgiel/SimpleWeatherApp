package com.jurgielewicz.simpleweatherapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Locale(val lat: Double, val long: Double) : Parcelable

@Parcelize
data class Periods(val timestamp: Long,
                   val dateTimeISO: String,
                   val tempC: Int?,
                   val maxTempC: Int,
                   val minTempC: Int,
                   val avgTempC: Int,
                   val humidity: Int,
                   val pop: Double?,
                   val feelsLikeC: Int,
                   val weather: String,
                   val weatherPrimary: String,
                   val icon: String,
                   val windSpeedKPH: Int,
                   val sunriseISO: String,
                   val sunsetISO: String) : Parcelable
@Parcelize
data class Response(val periods: List<Periods>, val locale: Locale) : Parcelable

@Parcelize
data class Result(val response: List<Response>) : Parcelable
