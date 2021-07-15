package com.vlprojects.weather.ui.weather

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vlprojects.weather.R
import com.vlprojects.weather.network.SevenTimerWeatherData

class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val timepoint: TextView = view.findViewById(R.id.timepointValue)
    val temperature: TextView = view.findViewById(R.id.temperatureValue)
    val weatherType: TextView = view.findViewById(R.id.weatherTypeValue)

    fun bind(weatherData: SevenTimerWeatherData) {
        timepoint.text = "${weatherData.timepointHour}:00"
        temperature.text = "${weatherData.temp}°C"
        weatherType.text = weatherData.weatherType
    }
}