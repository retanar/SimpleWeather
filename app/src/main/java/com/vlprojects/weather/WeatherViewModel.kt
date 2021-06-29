package com.vlprojects.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vlprojects.weather.network.SevenTimerWeatherApi
import com.vlprojects.weather.network.SevenTimerWeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {
    val responseString = MutableLiveData<String>("")

    fun getWeatherResponse(latitude: Double, longitude: Double) {
        responseString.value = "Loading..."

        SevenTimerWeatherApi.service.getCivilWeather(latitude, longitude).enqueue(object : Callback<SevenTimerWeatherResponse> {
            override fun onResponse(call: Call<SevenTimerWeatherResponse>, response: Response<SevenTimerWeatherResponse>) {
                val resp = response.body()

                responseString.value = resp?.dataSeries?.get(0)?.toString() ?: "null"

                Log.d("WeatherViewModel", resp?.init ?: "empty response")
            }

            override fun onFailure(call: Call<SevenTimerWeatherResponse>, t: Throwable) {
                responseString.value = "Failed: " + t.message
                Log.d("WeatherViewModel", t.stackTraceToString())
            }
        })
    }
}