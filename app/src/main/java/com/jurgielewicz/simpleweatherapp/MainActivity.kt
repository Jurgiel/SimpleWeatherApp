package com.jurgielewicz.simpleweatherapp

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.model.LatLng
import com.jurgielewicz.simpleweatherapp.R.id.*
import com.jurgielewicz.simpleweatherapp.adapters.ViewPagerAdapter
import com.jurgielewicz.simpleweatherapp.fragments.CurrentWeatherFragment
import com.jurgielewicz.simpleweatherapp.fragments.DailyWeatherFragment
import com.jurgielewicz.simpleweatherapp.utilities.WeatherApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var clientId: String
    private lateinit var clientSecret: String
    private val TAG = "MainActivity"
    private var dispose: Disposable? = null
    private val weatherApiService by lazy {
        WeatherApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clientId = getString(R.string.client_id)
        clientSecret = getString(R.string.client_id)

        setUpViewPager()

        val autocompleteFragment = fragmentManager.findFragmentById(R.id.autocomplete_fragment) as PlaceAutocompleteFragment
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place?) {
                search(p0?.latLng, 0)

            }

            override fun onError(p0: Status?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }
    fun search(latLng: LatLng?, i: Int) {
        //0- hourly, 1- daily

        dispose = weatherApiService
                .requestHourlyWeather(latLng!!.latitude, latLng!!.longitude, clientId, clientSecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.d(TAG.plus("  hourly"), result.response[0].periods[0].weather) },
                        { error -> Log.d("Searching error", error.message) }
                )

        dispose = weatherApiService
                .requestDailyWeather(latLng!!.latitude, latLng!!.longitude, clientId, clientSecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.d(TAG.plus("  daily"), result.response[0].periods[0].weather) },
                        { error -> Log.d("Searching error", error.message) }
                )
    }



    fun setUpViewPager(){
        var pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragments(CurrentWeatherFragment(), "Now")
        pagerAdapter.addFragments(DailyWeatherFragment(), "Daily")

        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        drawer_layout.closeDrawer(GravityCompat.START)

    }

    fun closeDrawer(){
        if(drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onPause() {
        dispose?.dispose()
        super.onPause()
    }

}
