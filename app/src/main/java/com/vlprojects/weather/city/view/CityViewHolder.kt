package com.vlprojects.weather.city.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vlprojects.weather.R
import com.vlprojects.weather.city.City

class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cityName: TextView = itemView.findViewById(R.id.cityName)

    fun bind(item: City, onClickListener: CityClickListener) {
        cityName.text = "${item.nameASCII}, ${item.country}"
        itemView.setOnClickListener { onClickListener.onClick(item) }
    }
}