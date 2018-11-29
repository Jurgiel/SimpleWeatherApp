package com.jurgielewicz.simpleweatherapp.database

import android.content.Context
import com.jurgielewicz.simpleweatherapp.models.Place
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class PlacesRepository(val context: Context) {

    fun getAll(): ArrayList<Place> = context.database.use {
        val places = ArrayList<Place>()

        select("PLACES_TABLE", "id", "name", "lat", "lng")
                .parseList(object : MapRowParser<List<Place>>{
                    override fun parseRow(columns: Map<String, Any?>): List<Place> {
                        val id = columns.getValue("id")
                        val name = columns.getValue("name")
                        val lat = columns.getValue("lat")
                        val lng = columns.getValue("lng")

                        val place = Place(id.toString().toLong(), name.toString(),
                                lat.toString().toDouble(), lng.toString().toDouble()))
                        places.add(place)

                        return places
                    }
                })
        places
    }

    fun addPlace(place: Place) = context.database.use {
        insert("PLACES_TABLE",
                "name" to place.name,
                "lat" to place.lat,
                "lng" to place.lng)
    }

    fun existCheck(place: Place) = context.database.use {
        select("PLACES_TABLE").
                whereArgs("lat = {lat} AND lng = {lng}",
                        args = *arrayOf("lat" to place, "lng" to place.lng))
    }
}