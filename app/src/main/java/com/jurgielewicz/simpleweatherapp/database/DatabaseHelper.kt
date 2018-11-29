package com.jurgielewicz.simpleweatherapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_PLACES_TABLE = ("CREATE TABLE $TABLE_PLACES($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_LAT REAL, " +
                "$COLUMN_LNG REAL")
        p0?.execSQL(CREATE_PLACES_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_PLACES")
    }

    fun getData():Cursor{
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_PLACES"
        val data = db.rawQuery(query, null)
        db.close()
        return data
    }

    fun addPlace(name: String, lat: Double, lng: Double){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_LAT, lat)
        values.put(COLUMN_LNG, lng)
        db.insert(TABLE_PLACES, null, values)
        db.close()
    }

<<<<<<< HEAD:app/src/main/java/com/jurgielewicz/simpleweatherapp/database/DatabaseHelper.kt
=======
    fun deletePlace(lat:Double, lng: Double){
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_PLACES WHERE $COLUMN_LAT = $lat AND $COLUMN_LNG = $lng")
        db.close()
    }

>>>>>>> parent of b5bc82d... row existCheck:app/src/main/java/com/jurgielewicz/simpleweatherapp/utilities/DatabaseHelper.kt
    companion object {

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "PlacesDB.db"
        private const val TABLE_PLACES = "places1"

        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_LAT = "lat"
        private const val COLUMN_LNG = "lng"
    }
}