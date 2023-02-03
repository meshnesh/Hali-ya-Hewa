package com.munene.haliyahewa.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.munene.haliyahewa.R
import com.munene.haliyahewa.base.BaseFragment
import com.munene.haliyahewa.data.models.UserLocation
import com.munene.haliyahewa.databinding.FragmentFavouriteForecastBinding
import com.munene.haliyahewa.ui.weather.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesForecastFragment : BaseFragment() {

    lateinit var binding: FragmentFavouriteForecastBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val args: FavouritesForecastFragmentArgs by navArgs()

    override fun onInitDataBinding() {
        val favourite = args.favourite
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favourite_forecast,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        getSelectedLocationWeather()

        return binding.root
    }

    /**
     * Get current weather for the selected Location.
     **/
    private fun getSelectedLocationWeather() {
        val selectedLocation = UserLocation(
            latitude = args.favourite.latitude,
            longitude = args.favourite.longitude
        )
        viewModel.getCurrentLocationWeather(selectedLocation)
    }
}