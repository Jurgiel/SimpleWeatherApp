package com.jurgielewicz.simpleweatherapp

import android.content.Context
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.facebook.stetho.Stetho
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.jurgielewicz.simpleweatherapp.adapters.SavedPlacesAdapter
import com.jurgielewicz.simpleweatherapp.adapters.ViewPagerAdapter
import com.jurgielewicz.simpleweatherapp.database.DatabaseHelper
import com.jurgielewicz.simpleweatherapp.fragments.CurrentWeatherFragment
import com.jurgielewicz.simpleweatherapp.fragments.DailyWeatherFragment
import com.jurgielewicz.simpleweatherapp.models.Periods
import com.jurgielewicz.simpleweatherapp.models.Places
import com.jurgielewicz.simpleweatherapp.models.Response
import com.jurgielewicz.simpleweatherapp.utilities.OnItemClickListener
import com.jurgielewicz.simpleweatherapp.utilities.WeatherApiService
import com.jurgielewicz.simpleweatherapp.utilities.addOnItemClickListener
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
    var details: List<Response>? = null
    lateinit var place: Places

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)

        clientId = getString(R.string.client_id)
        clientSecret = getString(R.string.client_secret)


        viewPagerLayout.visibility = View.INVISIBLE
        setUpViewPager()
        loadSavedPlaces(applicationContext)
        drawer_layout.openDrawer(GravityCompat.START)

        clientId = getString(R.string.client_id)
        clientSecret = getString(R.string.client_secret)


        val autocompleteFragment = fragmentManager.findFragmentById(R.id.autocomplete_fragment) as PlaceAutocompleteFragment
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: com.google.android.gms.location.places.Place?) {
                hourlySearched = false
                dailySearched = false
                place = Places(0, p0?.name.toString(), p0?.latLng?.latitude, p0?.latLng?.longitude)
                search(place, viewPager.currentItem)
                if(viewPager.currentItem == 0) hourlySearched = true
                else dailySearched = true
                closeDrawer()
            }

            override fun onError(p0: Status?) {
                Log.d(TAG, p0?.status.toString())
            }
        })

        viewPager.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                    if(viewPager.currentItem == 0 && !hourlySearched){
                        search(place, 0)
                        hourlySearched = true
                    }else if(viewPager.currentItem == 1 && !dailySearched){
                        search(place, 1)
                        dailySearched = true
                    }
            }
        })

        drawer_layout.addDrawerListener(object: DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerStateChanged(newState: Int) {
                if (newState == DrawerLayout.STATE_SETTLING) {
                    if (!drawer_layout.isDrawerOpen(GravityCompat.START)) {
                        loadSavedPlaces(applicationContext)
                    } else {
                        // Drawer started closing
                    }
                }
            }
        })
    }

    fun search(place: Places, i: Int) {
        //0- hourly, 1- daily
        viewPagerLayout.visibility = View.VISIBLE
        when (i) {
        0 -> dispose = weatherApiService
                .requestHourlyWeather(place.lat, place.lng, clientId, clientSecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> updateCurrentFragment(result.response?.get(0)?.periods, 0, place)  },
                        { error -> Log.d("Searching error", error.message) }
                )
        1 -> dispose = weatherApiService
                .requestDailyWeather(place.lat, place.lng, clientId, clientSecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> updateCurrentFragment(result.response?.get(0)?.periods, 1, place)
                                    details = result.response},
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

    private fun updateCurrentFragment(v: List<Periods>?, i: Int, place: Places){
        val fragment = supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.currentItem)
       when(i) {
           0->(fragment as CurrentWeatherFragment).updateRecView(v, place)
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

    fun loadSavedPlaces(context: Context){
        val db = DatabaseHelper(context)
        val listPlace = db.getData()
        recyclerView_NavBar.layoutManager = LinearLayoutManager(this)
        recyclerView_NavBar.adapter = SavedPlacesAdapter(listPlace)

        recyclerView_NavBar.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                place = listPlace[position]
                when(viewPager.currentItem){
                    0-> search(place, 0)
                    1-> search(place, 1)
                }
            closeDrawer()
                }

        })
    }

}
