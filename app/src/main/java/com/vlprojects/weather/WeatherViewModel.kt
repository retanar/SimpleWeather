package com.vlprojects.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vlprojects.weather.network.SevenTimerWeatherApi
import com.vlprojects.weather.network.SevenTimerWeatherData
import com.vlprojects.weather.network.SevenTimerWeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {
    private val weatherData = MutableLiveData<SevenTimerWeatherData?>(null)
    val timepointHour = Transformations.map(weatherData) { it?.timepointHour }
    val temperature = Transformations.map(weatherData) { it?.temp }
    val weatherType = Transformations.map(weatherData) { it?.weatherType }

    val responseStatus = MutableLiveData<String>("")

    fun sendWeatherRequest(latitude: Double, longitude: Double) {
        responseStatus.value = "Loading..."

        SevenTimerWeatherApi.service.getCivilWeather(latitude, longitude).enqueue(object : Callback<SevenTimerWeatherResponse> {
            override fun onResponse(call: Call<SevenTimerWeatherResponse>, response: Response<SevenTimerWeatherResponse>) {
                val resp = response.body()

                weatherData.value = resp?.dataSeries?.get(0)
                responseStatus.value = resp?.init

                Log.d("WeatherViewModel", resp?.init ?: "empty response")
            }

            override fun onFailure(call: Call<SevenTimerWeatherResponse>, t: Throwable) {
                responseStatus.value = "Failed: " + t.message
                Log.d("WeatherViewModel", t.stackTraceToString())
            }
        })
    }
}