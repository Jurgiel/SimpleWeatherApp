package com.jurgielewicz.simpleweatherapp.utilities


import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.jurgielewicz.simpleweatherapp.MainActivity
import com.jurgielewicz.simpleweatherapp.models.Response
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_hourly_weather.view.*
import java.text.SimpleDateFormat
import java.util.*


fun timestampConverter(num: Long, i: Int): String {
    // i = 0 - date
    // i = 1 - day
    // i== 2 - hour
    //i == 3 - day, date
    var format = "EEE"
    when(i){
        0 -> format = "dd/MM"
        1 -> format = "EEE"
        2 -> format = "HH:mm"
        3-> format = "EEE, dd/MM"
    }
    val formatter = SimpleDateFormat(format)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = num * 1000
    return formatter.format(calendar.time)
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

fun getDetails():List<Response>{
    return MainActivity().details!!
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





