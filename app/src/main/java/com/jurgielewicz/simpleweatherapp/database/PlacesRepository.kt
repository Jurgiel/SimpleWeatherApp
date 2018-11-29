package com.jurgielewicz.simpleweatherapp.database

import android.content.Context
import com.jurgielewicz.simpleweatherapp.models.Place
import com.jurgielewicz.simpleweatherapp.models.Places
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class PlacesRepository(val context: Context) {

    fun getAll(): ArrayList<Places> = context.database.use {
        val places = ArrayList<Places>()

        select("PLACES_TABLE", "id", "name", "lat", "lng")
                .parseList(object : MapRowParser<List<Places>>{
                    override fun parseRow(columns: Map<String, Any?>): List<Places> {
                        val id = columns.getValue("id")
                        val name = columns.getValue("name")
                        val lat = columns.getValue("lat")
                        val lng = columns.getValue("lng")

                        val place = Places(id.toString().toLong(), name.toString(),
                                lat.toString().toDouble(), lng.toString().toDouble())
                        places.add(place)

                        return places
                    }
                })
        places
    }

    fun addPlace(place: Places) = context.database.use {
        insert("PLACES_TABLE",
                "name" to place.name,
                "lat" to place.lat,
                "lng" to place.lng)
    }

//exists check
}