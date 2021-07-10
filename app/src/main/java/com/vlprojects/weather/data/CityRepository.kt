package com.vlprojects.weather.data

import android.content.Context
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.vlprojects.weather.R

object CityRepository {
    private lateinit var cities: List<City>

    fun loadDataset(context: Context) {
        if (CityRepository::cities.isInitialized) return

        val csvMapper = CsvMapper().apply { registerModule(KotlinModule()) }

        context.resources.openRawResource(R.raw.cities).bufferedReader().use { reader ->
            cities = csvMapper
                .readerFor(City::class.java)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues<City>(reader)
                .readAll()
        }
    }

    fun search(context: Context, partialName: String): List<City> {
        loadDataset(context)

        return cities.filter { city ->
            city.name.contains(partialName, ignoreCase = true)
        }
    }

    fun binarySearch(context: Context, cityId: Int): City {
        loadDataset(context)

        val index = cities.binarySearch { city -> city.id.compareTo(cityId) }
        return cities[index]
    }
}