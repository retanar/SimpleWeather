package com.vlprojects.weather.ui.citysearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlprojects.weather.R
import com.vlprojects.weather.data.City

class CityAdapter(private val onClickListener: CityClickListener) : RecyclerView.Adapter<CityViewHolder>() {

    constructor(onClick: (City) -> Unit) : this(CityClickListener(onClick))

    var cityList = listOf<City>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.city_item, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = cityList[position]
        holder.bind(item, onClickListener)
    }

    override fun getItemCount() = cityList.size
}

class CityClickListener(val onClick: (City) -> Unit)