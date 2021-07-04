package com.vlprojects.weather.city

import android.content.Context
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.vlprojects.weather.R

object CityRepository {
    private lateinit var cities: List<City>

    fun loadDataset(context: Context) {
        if (::cities.isInitialized) return

        val csvMapper = CsvMapper().apply { registerModule(KotlinModule()) }

        context.resources.openRawResource(R.raw.cities).bufferedReader().use { reader ->
            cities = csvMapper
                .readerFor(City::class.java)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues<City>(reader)
                .readAll()
                .toList()
        }
    }

    suspend fun search(context: Context, partialName: String): List<City> {
        loadDataset(context)

        return cities.filter { city ->
            city.name.contains(partialName, ignoreCase = true) ||
                    city.nameASCII.contains(partialName, ignoreCase = true)
        }
    }

    suspend fun binarySearch(context: Context, fullName: String): City {
        loadDataset(context)

        val index = cities.binarySearch { city -> city.name.compareTo(fullName) }
        return cities[index]
    }
}