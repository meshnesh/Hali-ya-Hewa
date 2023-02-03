package com.munene.haliyahewa.ui.weather

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.google.gson.Gson
import com.munene.haliyahewa.R
import com.munene.haliyahewa.base.BaseFragment
import com.munene.haliyahewa.data.datastore.HaliYaHewaStore
import com.munene.haliyahewa.databinding.FragmentWeatherBinding
import com.munene.haliyahewa.ui.forecast.ForecastAdapter
import com.munene.haliyahewa.util.extensions.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : BaseFragment() {

    lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val forecastAdapter by lazy { ForecastAdapter() }


    override fun onInitDataBinding() {
        setupToolbar()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        locationPermissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) ||
                            permissions.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                false
                            ) -> {
                        getCurrentLocationWeather()
                    }
                    else -> {
                        showLocationPermissionsDialog()
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.weatherForecast.adapter = forecastAdapter
        getCurrentLocationWeather()

        return binding.root
    }

    private fun setupToolbar() {
        binding.expandedToolbar.apply {
            inflateMenu(R.menu.menu_main)
            setOnMenuItemClickListener { menuItem ->
                menuItem.onNavDestinationSelected(findNavController()) ||
                        super.onOptionsItemSelected(menuItem)
            }
        }
    }

    /**
     * Get current weather for Location.*
     */
    private fun getCurrentLocationWeather() {
        if (checkPermissionsGranted()) {
            with(viewModel) {
                fetchLocationLiveData().observeOnce(viewLifecycleOwner) { currentLocation ->
                    if (currentLocation != null) {
                        HaliYaHewaStore.currentLocation = Gson().toJson(currentLocation)
                        getCurrentLocationWeather(currentLocation)
                    }
                }
            }
        } else {
            showLocationPermissionsDialog()
        }
    }
}