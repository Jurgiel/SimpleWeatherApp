package com.jurgielewicz.simpleweatherapp

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.model.LatLng
import com.jurgielewicz.simpleweatherapp.adapters.ViewPagerAdapter
import com.jurgielewicz.simpleweatherapp.fragments.CurrentWeatherFragment
import com.jurgielewicz.simpleweatherapp.fragments.DailyWeatherFragment
import com.jurgielewicz.simpleweatherapp.models.Response
import com.jurgielewicz.simpleweatherapp.utilities.WeatherApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(){
    private lateinit var clientId: String
    private lateinit var clientSecret: String
    private val TAG = "MainActivity"
    private var dispose: Disposable? = null
    private val weatherApiService by lazy {
        WeatherApiService.create()
    }
    private var hourlySearched = false
    private var dailySearched = false
    private var latLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clientId = getString(R.string.client_id)
        clientSecret = getString(R.string.client_secret)

        setUpViewPager()

        val autocompleteFragment = fragmentManager.findFragmentById(R.id.autocomplete_fragment) as PlaceAutocompleteFragment
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place?) {
                hourlySearched = false
                dailySearched = false
                latLng = p0?.latLng
                search(latLng, viewPager.currentItem)
                if(viewPager.currentItem == 0){
                    hourlySearched = true
                } else {
                    dailySearched = true
                }
                closeDrawer()
            }

            override fun onError(p0: Status?) {
                Log.d(TAG, p0?.status.toString())
            }
        })
        
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
                    if(viewPager.currentItem == 0 && hourlySearched == false && latLng != null){
                        search(latLng, 0)
                        Log.d("Debuging", "check if 0")
                        hourlySearched = true
                    }
                    if(viewPager.currentItem == 1 && dailySearched == false && latLng != null){
                        search(latLng, 1)
                        Log.d("Debuging", "check if 1")
                        dailySearched = true
                    }
            }
        })
    }
    fun search(latLng: LatLng?, i: Int) {
        //0- hourly, 1- daily
        when (i) {
        0 -> dispose = weatherApiService
                .requestHourlyWeather(latLng!!.latitude, latLng!!.longitude, clientId, clientSecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> updateCurrentFragment(result.response, 0)  },
                        { error -> Log.d("Searching error", error.message) }
                )
        1 -> dispose = weatherApiService
                .requestDailyWeather(latLng!!.latitude, latLng!!.longitude, clientId, clientSecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> updateCurrentFragment(result.response, 1) },
                        { error -> Log.d("Searching error", error.message) }
                )
        }
    }

    fun setUpViewPager(){
        var pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragments(CurrentWeatherFragment(), "Now")
        pagerAdapter.addFragments(DailyWeatherFragment(), "Daily")

        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        drawer_layout.closeDrawer(GravityCompat.START)

    }


    private fun updateCurrentFragment(v: List<Response>, i: Int){
        val fragment = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.currentItem)
       when(i) {
           0->(fragment as CurrentWeatherFragment).updateRecView(v)
           1->(fragment as DailyWeatherFragment).updateRecView(v)
       }
    }

    override fun onPause() {
        dispose?.dispose()
        super.onPause()
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
}

