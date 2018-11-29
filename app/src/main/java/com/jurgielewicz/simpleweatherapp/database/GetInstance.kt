package com.jurgielewicz.simpleweatherapp.database

import android.content.Context

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)