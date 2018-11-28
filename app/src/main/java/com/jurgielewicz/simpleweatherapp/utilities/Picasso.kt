package com.jurgielewicz.simpleweatherapp.utilities

import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun downloadImage(imageUrl: String, imageView: ImageView, width: Int, height: Int){
    try{
        Picasso.get()
                .load(imageUrl)
                .resize(width, height)
                .centerInside()
                .into(imageView)
    } catch (e: Exception) {
        Log.d("downloadImage", e.message)
    }
}

fun getIconUrl(iconId: String): String{
    return  "https://cdn.aerisapi.com/wxicons/v2/$iconId"
}
