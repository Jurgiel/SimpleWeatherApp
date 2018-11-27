package com.jurgielewicz.simpleweatherapp.utilities


import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

fun timeConverter(string: String, i: Int): String {
    val isoFormat = "yyyy-MM-dd'T'HH:mm:ss"
    var expectedFormat = "dd/MM"
    when(i){
        0 -> expectedFormat = "dd/MM"
        1 -> expectedFormat = "EEE"
        2 -> expectedFormat = "HH:mm"
        3 -> expectedFormat = "EEE, dd/MM"
    }
    val dateFormat = SimpleDateFormat(isoFormat, Locale.getDefault())
    var date = dateFormat.parse(string)
    return SimpleDateFormat(expectedFormat).format(date)
}

interface OnItemClickListener {
    fun onItemClicked(position: Int, view: View)
}

fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
    this.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewDetachedFromWindow(view: View?) {
            view?.setOnClickListener(null)
        }

        override fun onChildViewAttachedToWindow(view: View?) {
            view?.setOnClickListener {
                val holder = getChildViewHolder(view)
                onClickListener.onItemClicked(holder.adapterPosition, view)
            }
        }
    })
}

fun downloadImage(imageUrl: String, imageView: ImageView, width: Int, height: Int){
    try{
        Picasso.get()
                .load(imageUrl)
                .resize(width, height)
                .centerInside()
                .into(imageView)
    } catch (e: Exception) {
        Log.d("DailyWeatherAdapter", e.message)
    }
}

fun getIconUrl(iconId: String): String{
    return  "https://cdn.aerisapi.com/wxicons/v2/$iconId"
}
