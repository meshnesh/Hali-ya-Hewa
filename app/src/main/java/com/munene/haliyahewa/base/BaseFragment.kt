package com.munene.haliyahewa.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.munene.haliyahewa.R

abstract class BaseFragment : Fragment() {

    lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    /**
     * Called to Initialize view data binding variables when fragment view is created.
     */
    abstract fun onInitDataBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitDataBinding()
    }

    fun checkPermissionsGranted(): Boolean =
        ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    fun showLocationPermissionsDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.location_permissions)
            .setMessage(resources.getString(R.string.location_permissions_description))
            .setPositiveButton(resources.getString(R.string.action_accept)) { _, _ ->
                getCurrentLocation()
            }
            .setNegativeButton(resources.getString(R.string.action_decline)) { _, _ -> }
            .show()
    }

    /**
     * Check if Location Permissions are granted before getting current Location
     **/
    private fun getCurrentLocation() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    /** Navigate back to parent graph */
    fun navigateBack(destinationId: Int) {
        val host = requireActivity().findViewById<View>(R.id.nav_host_container)
        /** Navigate the custoner when the host is not null */
        if (host != null) {
            Navigation.findNavController(host).popBackStack(destinationId, false)
        }
    }

    fun openWifiSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val wifiSettingsIntent = Intent(Settings.Panel.ACTION_WIFI)
            startActivityForResult(wifiSettingsIntent, 1000)
        } else {
            val wifiSettingsIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivity(wifiSettingsIntent)
        }
    }
}