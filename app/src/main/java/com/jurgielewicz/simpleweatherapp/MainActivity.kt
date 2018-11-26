package com.jurgielewicz.simpleweatherapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.PagerAdapter
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.jurgielewicz.simpleweatherapp.R.id.tabLayout
import com.jurgielewicz.simpleweatherapp.R.id.viewPager
import com.jurgielewicz.simpleweatherapp.adapters.ViewPagerAdapter
import com.jurgielewicz.simpleweatherapp.fragments.CurrentWeatherFragment
import com.jurgielewicz.simpleweatherapp.fragments.DailyWeatherFragment
import com.jurgielewicz.simpleweatherapp.utilities.WeatherApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var clientId: String
    private lateinit var clientSecret: String

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
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(p0: Status?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })


    }

    fun setUpViewPager(){
        var pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragments(CurrentWeatherFragment(), "Now")
        pagerAdapter.addFragments(DailyWeatherFragment(), "Daily")

        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        drawer_layout.openDrawer(GravityCompat.START)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
