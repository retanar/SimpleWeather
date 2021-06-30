package com.vlprojects.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlprojects.weather.network.SevenTimerWeatherApi
import com.vlprojects.weather.network.SevenTimerWeatherData
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherData = MutableLiveData<SevenTimerWeatherData?>(null)
    val timepointHour = Transformations.map(weatherData) { it?.timepointHour }
    val temperature = Transformations.map(weatherData) { it?.temp }
    val weatherType = Transformations.map(weatherData) { it?.weatherType }

    val responseStatus = MutableLiveData<String>("")

    fun sendWeatherRequest(latitude: Double, longitude: Double) {
        responseStatus.value = "Loading..."

        viewModelScope.launch {
            try {
                val resp = SevenTimerWeatherApi.service.getCivilWeather(latitude, longitude)

                weatherData.value = resp.dataSeries[0]
                responseStatus.value = resp.init

                Log.d("WeatherViewModel", resp.init)
            } catch (e: Exception) {
                responseStatus.value = "Failed to load the data: " + e.message
                Log.d("WeatherViewModel", e.stackTraceToString())
            }
        }
    }
}