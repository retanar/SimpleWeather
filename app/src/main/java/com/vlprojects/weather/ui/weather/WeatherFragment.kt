package com.vlprojects.weather.ui.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.*
import com.vlprojects.weather.R
import com.vlprojects.weather.data.City
import com.vlprojects.weather.data.CityPreferenceRepository
import com.vlprojects.weather.databinding.WeatherFragmentBinding
import com.vlprojects.weather.ui.citysearch.CITY_BUNDLE_KEY
import com.vlprojects.weather.ui.citysearch.CITY_REQUEST_KEY
import com.vlprojects.weather.ui.citysearch.SearchCityFragment

const val CITY_PREFERENCES_NAME = "city_preference"

class WeatherFragment : Fragment() {

    private lateinit var binding: WeatherFragmentBinding
    private val Context.dataStore by preferencesDataStore(CITY_PREFERENCES_NAME)
    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(CityPreferenceRepository(requireContext().dataStore), requireContext())
    }
    private val weatherAdapter = WeatherAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedState: Bundle?): View {
        binding = WeatherFragmentBinding.inflate(inflater, container, false)

        setObservers()
        binding.sendRequest.setOnClickListener { onSendRequest() }
        binding.cityName.setOnClickListener { openSearchFragment() }
        binding.refreshLayout.setOnRefreshListener { onSendRequest() }

        binding.weatherRecyclerView.adapter = weatherAdapter

        setFragmentResultListener(CITY_REQUEST_KEY, ::resultListener)

        return binding.root
    }

    private fun onSendRequest() {
        viewModel.city.value?.let { city ->
            viewModel.sendWeatherRequest(city.lat, city.lon)
        }
    }

    private fun setObservers() {
        viewModel.weatherDataList.observe(viewLifecycleOwner) { list ->
            list?.let {
                weatherAdapter.weatherList = it
            }
        }

        viewModel.responseStatus.observe(viewLifecycleOwner) { responseStatus ->
            val (status, isRefreshing) = when (responseStatus!!) {
                ResponseStatus.LOADING -> resources.getString(R.string.loading) to true
                ResponseStatus.DEFAULT -> resources.getString(R.string.data_not_loaded) to false
                ResponseStatus.FAILED -> resources.getString(R.string.failed_to_load_data) to false
                ResponseStatus.OK -> resources.getString(R.string.loaded) to false
            }
            binding.statusValue.text = status
            binding.refreshLayout.isRefreshing = isRefreshing
        }
        viewModel.city.observe(viewLifecycleOwner) { city ->
            binding.cityName.text = city.name
            binding.userLatitude.setText(city.lat.toString())
            binding.userLongitude.setText(city.lon.toString())
            onSendRequest()
        }
    }

    private fun openSearchFragment() {
        parentFragmentManager.commit {
            replace<SearchCityFragment>(R.id.mainFragmentContainer)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun resultListener(requestKey: String, bundle: Bundle) {
        val result = bundle.get(CITY_BUNDLE_KEY) as City
        viewModel.city.value = result
        viewModel.saveCity(result)
    }
}