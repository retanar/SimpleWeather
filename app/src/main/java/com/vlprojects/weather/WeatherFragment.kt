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

        return binding.root
    }

}