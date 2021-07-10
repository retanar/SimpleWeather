package com.vlprojects.weather.ui.citysearch

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vlprojects.weather.R
import com.vlprojects.weather.data.City

class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cityName: TextView = itemView.findViewById(R.id.cityName)

    fun bind(item: City, onClickListener: CityClickListener) {
        cityName.text = "${item.name}, ${item.country}"
        itemView.setOnClickListener { onClickListener.onClick(item) }
    }
}