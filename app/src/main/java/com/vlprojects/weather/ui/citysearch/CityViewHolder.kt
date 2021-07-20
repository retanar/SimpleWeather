package com.vlprojects.weather.ui.citysearch

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vlprojects.weather.R
import com.vlprojects.weather.data.City

class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cityName: TextView = itemView.findViewById(R.id.cityName)
    val country: TextView = itemView.findViewById(R.id.country)
    val description: TextView = itemView.findViewById(R.id.description)

    fun bind(city: City, onClickListener: CityClickListener) {
        cityName.text = city.name
        country.text = city.country
        description.text = city.description
        itemView.setOnClickListener { onClickListener.onClick(city) }
    }
}