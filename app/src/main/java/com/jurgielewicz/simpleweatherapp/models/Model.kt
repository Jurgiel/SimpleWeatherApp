package com.jurgielewicz.simpleweatherapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Places(val id: Int?, val name: String?, val lat: Double?, val lng: Double?):Parcelable

@Parcelize
data class Periods(val timestamp: Long?,
                   val dateTimeISO: String?,
                   val tempC: Int?,
                   val maxTempC: Int?,
                   val minTempC: Int?,
                   val avgTempC: Int?,
                   val humidity: Int?,
                   val pop: Double?,
                   val feelsLikeC: Int?,
                   val weather: String?,
                   val weatherPrimary: String?,
                   val icon: String?,
                   val windSpeedKPH: Int?,
                   val sunriseISO: String?,
                   val sunsetISO: String?) : Parcelable
@Parcelize
data class Response(val periods: List<Periods>?) : Parcelable

@Parcelize
data class Result(val response: List<Response>?) : Parcelable
