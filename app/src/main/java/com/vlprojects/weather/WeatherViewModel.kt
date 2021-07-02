package com.vlprojects.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlprojects.weather.network.SevenTimerWeatherApi
import com.vlprojects.weather.network.SevenTimerWeatherData
import com.vlprojects.weather.network.SevenTimerWeatherResponse
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.ceil

class WeatherViewModel : ViewModel() {
    private val eightDayWeatherData = MutableLiveData<SevenTimerWeatherResponse?>(null)
    private val weatherData = Transformations.map(eightDayWeatherData) {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val index = currentHour / 3
        it?.dataSeries?.get(index)
    }
    val timepointHour = Transformations.map(weatherData) { it?.timepointHour }
    val temperature = Transformations.map(weatherData) { it?.temp }
    val weatherType = Transformations.map(weatherData) { it?.weatherType }

    val responseStatus = MutableLiveData<String>("")

    fun sendWeatherRequest(latitude: Double, longitude: Double) {
        responseStatus.value = "Loading..."

        viewModelScope.launch {
            try {
                val resp = SevenTimerWeatherApi.service.getCivilWeather(latitude, longitude)
                eightDayWeatherData.value = resp
                responseStatus.value = resp.init

                Log.d("WeatherViewModel", resp.init)
            } catch (e: Exception) {
                responseStatus.value = "Failed to load the data: " + e.message
                Log.d("WeatherViewModel", e.stackTraceToString())
            }
        }
    }
}