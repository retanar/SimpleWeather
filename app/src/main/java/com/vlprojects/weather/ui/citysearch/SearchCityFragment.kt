package com.vlprojects.weather.ui.citysearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.vlprojects.weather.databinding.SearchCityFragmentBinding

const val CITY_REQUEST_KEY = "requestCity"
const val CITY_BUNDLE_KEY = "chosenCity"

class SearchCityFragment : Fragment() {

    lateinit var binding: SearchCityFragmentBinding
    val viewModel: SearchCityViewModel by viewModels { SearchCityViewModelFactory(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SearchCityFragmentBinding.inflate(inflater, container, false)

        val cityAdapter = CityAdapter { city ->
            setFragmentResult(CITY_REQUEST_KEY, bundleOf(CITY_BUNDLE_KEY to city))
            parentFragmentManager.popBackStack()
        }
        binding.citiesRecyclerView.adapter = cityAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(input: String?): Boolean {
                cityAdapter.cityList = viewModel.searchCity(input)

                return true
            }

            override fun onQueryTextChange(input: String?) = true
        })

        return binding.root
    }
}