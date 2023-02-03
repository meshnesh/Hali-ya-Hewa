package com.munene.haliyahewa.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.ktx.widget.PlaceSelectionError
import com.google.android.libraries.places.ktx.widget.PlaceSelectionSuccess
import com.google.android.libraries.places.ktx.widget.placeSelectionEvents
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.munene.haliyahewa.R
import com.munene.haliyahewa.base.BaseFragment
import com.munene.haliyahewa.data.db.entities.Favorite
import com.munene.haliyahewa.databinding.FragmentFavouritesBinding
import com.munene.haliyahewa.util.recyclerview.GridSpacingItemDecoration
import com.munene.haliyahewa.util.recyclerview.RecyclerViewClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : BaseFragment(), RecyclerViewClickListener {

    lateinit var binding: FragmentFavouritesBinding
    private lateinit var placesClient: PlacesClient
    private val favoritesAdapter by lazy { FavoritesAdapter(this) }
    private val viewModel: FavoritesViewModel by viewModels()

    lateinit var favoriteLocations: ArrayList<Favorite>

    override fun onInitDataBinding() {
        loadFavorites()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false)

        placesClient = Places.createClient(requireContext())

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.NAME,
                Place.Field.ID,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
        )

        // Listen to place selection events
        lifecycleScope.launchWhenCreated {
            autocompleteFragment.placeSelectionEvents().collect { event ->
                when (event) {
                    is PlaceSelectionSuccess -> {
                        val place = event.place
                        val favorite = Favorite(
                            latitude = place.latLng?.latitude!!,
                            longitude = place.latLng?.longitude!!,
                            address = place.address!!,
                            name = place.name!!
                        )

                        val directions =
                            FavouritesFragmentDirections.actionFavouritesFragmentToFavouriteForecastFragment(
                                favourite = favorite
                            )


                        binding.csLocation.visibility = View.VISIBLE
                        binding.tvLocationName.text = place.name

                        binding.tvLocationName.setOnClickListener {
                            findNavController().navigate(directions)
                        }

                        binding.csFavourite.setOnClickListener {
                            viewModel.saveLocation(favorite)
                        }
                    }
                    is PlaceSelectionError -> Toast.makeText(
                        requireContext(),
                        "Failed to get place '${event.status.statusMessage}'",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.favoritesList.apply {
            adapter = favoritesAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            addItemDecoration(
                GridSpacingItemDecoration(
                    requireContext(),
                    spanCount = 2,
                    spacing = R.dimen.keyline_4,
                    includeEdge = true,
                    headerNum = 0
                )
            )
        }

        return binding.root
    }

    private fun loadFavorites() {
        lifecycleScope.launch {
            try {
                viewModel.getLocations().collect {
                    favoriteLocations = it as ArrayList<Favorite>
                    favoritesAdapter.updateFavorites(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onClick(data: Any?) {
        data as Favorite
        val directions =
            FavouritesFragmentDirections.actionFavouritesFragmentToFavouriteForecastFragment(
                favourite = data
            )
        findNavController().navigate(directions)
    }
}
