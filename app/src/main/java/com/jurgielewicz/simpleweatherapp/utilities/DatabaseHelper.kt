package com.jurgielewicz.simpleweatherapp.utilities

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
        return data
    }

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