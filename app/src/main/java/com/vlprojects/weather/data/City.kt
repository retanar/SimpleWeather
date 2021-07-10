package com.vlprojects.weather.data

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    @JsonProperty("city")
    val name: String,
    val description: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    @JsonProperty("iso2")
    val isoCode: String,
    val id: Int,
) : Parcelable
