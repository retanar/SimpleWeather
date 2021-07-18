package com.vlprojects.weather.data

import android.content.Context
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.vlprojects.weather.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityRepository private constructor(context: Context) {

    private val cities: List<City> = getDataset(context)

    private fun getDataset(context: Context): List<City> {
        val csvMapper = CsvMapper().apply { registerModule(KotlinModule()) }

        context.resources.openRawResource(R.raw.cities).bufferedReader().use { reader ->
            return csvMapper
                .readerFor(City::class.java)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues<City>(reader)
                .readAll()
        }
    }

    fun search(partialName: String): List<City> {
        return cities.filter { city ->
            city.name.contains(partialName, ignoreCase = true)
        }
    }

    fun binarySearch(cityId: Int): City {
        val index = cities.binarySearch { city -> city.id.compareTo(cityId) }
        return cities[index]
    }

    companion object {
        private var instance: CityRepository? = null

        suspend fun getInstance(context: Context): CityRepository {
            if (instance == null) {
                withContext(Dispatchers.IO) {
                    instance = CityRepository(context)
                }
            }

            return instance!!
        }
    }
}