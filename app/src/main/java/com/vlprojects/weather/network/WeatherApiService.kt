package com.vlprojects.weather.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://www.7timer.info/bin/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET("api.pl?product=civil&output=json")
    fun getCivilWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): Call<String>
}

object WeatherApi {
    val service: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}