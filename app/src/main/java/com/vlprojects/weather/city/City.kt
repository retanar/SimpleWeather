package com.vlprojects.weather.city

data class City(
    val name: String,
    val nameASCII: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val isoCode: String,
)
