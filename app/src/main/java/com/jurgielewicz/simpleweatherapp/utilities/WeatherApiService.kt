package com.jurgielewicz.simpleweatherapp.utilities

import com.jurgielewicz.simpleweatherapp.models.Result
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecasts/{lat},{lng}?limit=14&")
    fun requestDailyWeather(@Path("lat") lat: Double?,
                            @Path("lng") lng: Double?,
                            @Query("client_id") client_id: String,
                            @Query("client_secret") client_secret: String):Observable<Result>

    @GET("forecasts/{lat},{lng}?filter=1hr&limit=24&")
    fun requestHourlyWeather(@Path("lat") lat: Double?,
                             @Path("lng") lng: Double?,
                             @Query("client_id") client_id: String,
                             @Query("client_secret") client_secret: String):Observable<Result>


    companion object {
        fun create(): WeatherApiService{
            val retrofit = Retrofit
                    .Builder()
                    .addConverterFactory(
                            MoshiConverterFactory.create())
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .baseUrl("https://api.aerisapi.com/")
                    .build()

            return retrofit.create(WeatherApiService::class.java)
        }
    }
}