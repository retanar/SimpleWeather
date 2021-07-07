package com.vlprojects.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import com.vlprojects.weather.city.City
import com.vlprojects.weather.city.view.SearchCityFragment
import com.vlprojects.weather.databinding.WeatherFragmentBinding

class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: WeatherFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedState: Bundle?): View {
        binding = WeatherFragmentBinding.inflate(inflater, container, false)

        setObservers()
        binding.sendRequest.setOnClickListener { onSendRequest() }
        binding.cityName.setOnClickListener {
            parentFragmentManager.commit {
                replace<SearchCityFragment>(R.id.mainFragmentContainer)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }
        binding.refreshLayout.setOnRefreshListener { onSendRequest() }

        setFragmentResultListener("requestCity", ::resultListener)

        return binding.root
    }

    private fun onSendRequest() {
        // Default location - London
        viewModel.sendWeatherRequest(
            binding.userLatitude.text.toString().toDouble(),
            binding.userLongitude.text.toString().toDouble()
        )
    }

    private fun setObservers() {
        viewModel.timepointHour.observe(viewLifecycleOwner) { timepoint ->
            binding.timepointValue.text = timepoint?.toString() ?: "none"
        }
        viewModel.temperature.observe(viewLifecycleOwner) { temp ->
            binding.temperatureValue.text = temp?.toString() ?: "none"
        }
        viewModel.weatherType.observe(viewLifecycleOwner) { type ->
            binding.weatherTypeValue.text = type ?: "none"
        }
        viewModel.responseStatus.observe(viewLifecycleOwner) { responseStatus ->
            val (status, isRefreshing) = when (responseStatus!!) {
                ResponseStatus.LOADING -> "Loading..." to true
                ResponseStatus.DEFAULT -> "Data is not loaded" to false
                ResponseStatus.FAILED -> "Failed to load the data" to false
                ResponseStatus.OK -> "Loaded" to false
            }
            binding.statusValue.text = status
            binding.refreshLayout.isRefreshing = isRefreshing
        }
    }

    private fun resultListener(requestKey: String, bundle: Bundle) {
        val result = bundle.get("chosenCity") as City
        binding.userLatitude.setText(result.lat.toString())
        binding.userLongitude.setText(result.lon.toString())
        binding.cityName.text = result.nameASCII
    }
}