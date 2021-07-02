package com.vlprojects.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vlprojects.weather.databinding.WeatherFragmentBinding

class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: WeatherFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedState: Bundle?): View {
        binding = WeatherFragmentBinding.inflate(inflater, container, false)

        setObservers()
        binding.sendRequest.setOnClickListener { onSendRequest() }
        binding.refreshLayout.setOnRefreshListener { onSendRequest() }

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
            val (status, isRefreshing) = when(responseStatus!!) {
                ResponseStatus.LOADING -> "Loading..." to true
                ResponseStatus.DEFAULT -> "Data is not loaded" to false
                ResponseStatus.FAILED -> "Failed to load the data" to false
                ResponseStatus.OK -> "Loaded" to false
            }
            binding.statusValue.text = status
            binding.refreshLayout.isRefreshing = isRefreshing
        }
    }
}