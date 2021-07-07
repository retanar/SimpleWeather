package com.vlprojects.weather.city.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.vlprojects.weather.city.CityRepository
import com.vlprojects.weather.databinding.SearchCityFragmentBinding
import kotlinx.coroutines.launch

class SearchCityFragment : Fragment() {

    lateinit var binding: SearchCityFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SearchCityFragmentBinding.inflate(inflater, container, false)

        val cityAdapter = CityAdapter { city ->
            Toast.makeText(context, city.nameASCII, Toast.LENGTH_SHORT).show()
            setFragmentResult("requestCity", bundleOf("chosenCity" to city as Any))
            parentFragmentManager.popBackStack()
        }
        binding.citiesRecyclerView.adapter = cityAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(input: String?): Boolean {
                lifecycleScope.launch {
                    cityAdapter.cityList = CityRepository.search(context!!, input ?: "")
                }

                return true
            }

            override fun onQueryTextChange(input: String?) = true
        })

        return binding.root
    }
}