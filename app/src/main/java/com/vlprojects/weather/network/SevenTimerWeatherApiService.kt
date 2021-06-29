package com.vlprojects.weather.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val SEVEN_TIMER_URL = "http://www.7timer.info/bin/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// http client needed for custom timeout
private val httpClient = OkHttpClient.Builder()
    .connectTimeout(12, TimeUnit.SECONDS)
    .readTimeout(12, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(SEVEN_TIMER_URL)
    .client(httpClient)
    .build()

interface SevenTimerWeatherApiService {
    @GET("api.pl?product=civil&output=json")
    fun getCivilWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): Call<SevenTimerWeatherResponse>
}

object SevenTimerWeatherApi {
    val service: SevenTimerWeatherApiService by lazy {
        retrofit.create(SevenTimerWeatherApiService::class.java)
    }
}