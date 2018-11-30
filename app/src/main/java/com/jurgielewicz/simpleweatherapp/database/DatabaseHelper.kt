package com.jurgielewicz.simpleweatherapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.jurgielewicz.simpleweatherapp.models.Places

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_PLACES_TABLE = ("CREATE TABLE $TABLE_PLACES($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_LAT REAL, " +
                "$COLUMN_LNG REAL)")
        p0?.execSQL(CREATE_PLACES_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_PLACES")
    }

    fun getData():List<Places>{
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_PLACES"
        val cursor = db.rawQuery(query, null)
        val placesList = ArrayList<Places>()

        if(cursor.moveToFirst()){
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val lat = cursor.getDouble(2)
                val lng = cursor.getDouble(3)

               val place = Places(id, name, lat, lng)
                placesList.add(place)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return placesList
    }

    fun addPlace(place: Places){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, place.name)
        values.put(COLUMN_LAT, place.lat)
        values.put(COLUMN_LNG, place.lng)
        db.insert(TABLE_PLACES, null, values)
        db.close()
    }


    fun deletePlace(place: Places) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_PLACES WHERE $COLUMN_ID = ${place.id}")
        db.close()
    }


    fun existsCheck(place: Places): Boolean {
        val db= this.readableDatabase
        val query = "SELECT * FROM $TABLE_PLACES WHERE $COLUMN_LAT = ${place.lat} AND $COLUMN_LNG = ${place.lng}"
        val cursor = db.rawQuery(query, null)
        if(cursor.count > 0){
            cursor.close()
            db.close()
            return true
        }
        cursor.close()
        db.close()
        return false
    }

    companion object {

        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "PlacesDB.db"
        private const val TABLE_PLACES = "Table_Places"

        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_LAT = "lat"
        private const val COLUMN_LNG = "lng"
    }
}