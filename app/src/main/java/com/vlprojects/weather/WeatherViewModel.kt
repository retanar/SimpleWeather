package com.vlprojects.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vlprojects.weather.network.WeatherApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {
    val responseString = MutableLiveData<String>("")

    fun getWeatherResponse(latitude: Double, longitude: Double) {
        WeatherApi.service.getCivilWeather(latitude, longitude).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                responseString.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                responseString.value = "Failed: " + t.message
            }
        })
    }
}