package com.jurgielewicz.simpleweatherapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jurgielewicz.simpleweatherapp.R
import com.jurgielewicz.simpleweatherapp.models.Places
import com.jurgielewicz.simpleweatherapp.models.ViewHolder
import kotlinx.android.synthetic.main.row_saved_place.view.*

class SavedPlacesAdapter(val places: List<Places>): RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.row_saved_place, parent, false)

        return ViewHolder(row)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.savedCityTextView.text = places[position].name
    }
}