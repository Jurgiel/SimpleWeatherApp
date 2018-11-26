package com.jurgielewicz.simpleweatherapp.utilities


import android.support.v7.widget.RecyclerView
import android.view.View
import com.jurgielewicz.simpleweatherapp.MainActivity
import com.jurgielewicz.simpleweatherapp.models.Response
import java.text.SimpleDateFormat
import java.util.*


fun timestampConverter(num: Long, i: Int): String {
    // i = 0 - date
    // i = 1 - day
    // i== 2 - hour
    var format = "EEE"
    when(i){
        0 -> format = "dd/MM"
        1 -> format = "EEE"
        2 -> format = "HH:mm"
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
            view?.setOnClickListener({
                val holder = getChildViewHolder(view)
                onClickListener.onItemClicked(holder.adapterPosition, view)
            })
        }
    })
}

fun getDetails():List<Response>{
    return MainActivity().details!!
}





