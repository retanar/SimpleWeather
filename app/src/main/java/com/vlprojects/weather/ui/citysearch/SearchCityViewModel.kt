package com.vlprojects.weather.ui.citysearch

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlprojects.weather.data.City
import com.vlprojects.weather.data.CityRepository
import kotlinx.coroutines.launch

class SearchCityViewModel(context: Context) : ViewModel() {

    lateinit var cityRepository: CityRepository

    init {
        viewModelScope.launch {
            cityRepository = CityRepository.getInstance(context)
        }
    }

    fun searchCity(partialName: String?): List<City> {
        if (!::cityRepository.isInitialized)
            return listOf()
        return cityRepository.search(partialName ?: "")
    }
}