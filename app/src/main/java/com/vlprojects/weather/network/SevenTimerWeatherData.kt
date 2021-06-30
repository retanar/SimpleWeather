package com.vlprojects.weather.network

import com.squareup.moshi.Json

data class SevenTimerWeatherResponse(
    val product: String,
    val init: String,
    @Json(name = "dataseries")
    val dataSeries: List<SevenTimerWeatherData>,
)

data class SevenTimerWeatherData(
    @Json(name = "timepoint")
    val timepointHour: Int,
    @Json(name = "cloudcover")
    val cloudCover: Int,
    @Json(name = "prec_type")
    val precType: String,
    @Json(name = "prec_amount")
    val precAmount: Int,
    @Json(name = "temp2m")
    val temp: Int,
    @Json(name = "rh2m")
    val humidity: String,
    @Json(name = "weather")
    val weatherType: String,
)
