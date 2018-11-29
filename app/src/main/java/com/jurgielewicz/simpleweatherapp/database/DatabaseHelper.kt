package com.jurgielewicz.simpleweatherapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseHelper(context: Context) : ManagedSQLiteOpenHelper(context, "PlaceDatabase", null, 1 ){
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseHelper{
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
                "PLACES_TABLE", true,
                "id" to INTEGER + PRIMARY_KEY + UNIQUE,
                "name" to TEXT,
                "lat" to REAL,
                "lng" to REAL)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.dropTable("PLACES_TABLE", true)
    }
}