package com.vlprojects.weather.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val SEVEN_TIMER_URL = "http://www.7timer.info/bin/"
private const val CONNECTION_TIMEOUT = 60L

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// http client needed for custom timeout
private val httpClient = OkHttpClient.Builder()
    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(SEVEN_TIMER_URL)
    .client(httpClient)
    .build()

interface SevenTimerWeatherApiService {
    @GET("api.pl?product=civil&output=json")
    suspend fun getCivilWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): SevenTimerWeatherResponse
}

object SevenTimerWeatherApi {
    val service: SevenTimerWeatherApiService by lazy {
        retrofit.create(SevenTimerWeatherApiService::class.java)
    }
}