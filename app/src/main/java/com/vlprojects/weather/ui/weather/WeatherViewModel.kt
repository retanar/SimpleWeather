package com.vlprojects.weather.ui.weather

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.vlprojects.weather.data.City
import com.vlprojects.weather.data.CityPreferenceRepository
import com.vlprojects.weather.data.CityRepository
import com.vlprojects.weather.network.SevenTimerWeatherApi
import com.vlprojects.weather.network.SevenTimerWeatherData
import com.vlprojects.weather.network.SevenTimerWeatherResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Calendar

class WeatherViewModel(
    private val cityPrefRepository: CityPreferenceRepository,
    context: Context
) : ViewModel() {

    private val eightDayWeatherData = MutableLiveData<SevenTimerWeatherResponse?>(null)
    val weatherDataList: LiveData<List<SevenTimerWeatherData>?>
        get() = Transformations.map(eightDayWeatherData) {
            it?.dataSeries?.take(8)
        }

    private val weatherData = Transformations.map(eightDayWeatherData) {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val index = currentHour / 3
        it?.dataSeries?.get(index)
    }
    val timepointHour = Transformations.map(weatherData) { it?.timepointHour }
    val temperature = Transformations.map(weatherData) { it?.temp }
    val weatherType = Transformations.map(weatherData) { it?.weatherType }

    val responseStatus = MutableLiveData(ResponseStatus.DEFAULT)

    val city = MutableLiveData<City>()

    init {
        viewModelScope.launch {
            cityPrefRepository.cityPreference.collect { cityPref ->
                val city = CityRepository.binarySearch(context, cityPref.id)
                this@WeatherViewModel.city.value = city
            }
        }
    }

    fun sendWeatherRequest(latitude: Double, longitude: Double) {
        responseStatus.value = ResponseStatus.LOADING

        viewModelScope.launch {
            try {
                val resp = SevenTimerWeatherApi.service.getCivilWeather(latitude, longitude)
                eightDayWeatherData.value = resp
                responseStatus.value = ResponseStatus.OK

                Log.d("WeatherViewModel", resp.init)
            } catch (e: Exception) {
                responseStatus.value = ResponseStatus.FAILED
                Log.d("WeatherViewModel", e.stackTraceToString())
            }
        }
    }

    fun saveCity(newCity: City? = city.value) {
        newCity?.let {
            viewModelScope.launch {
                cityPrefRepository.updateCityId(newCity.id)
            }
        }
    }
}