package com.example.googlemapsapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.gms.common.GoogleApiAvailabilityLight
import com.google.android.gms.location.*
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.fragment_one.*
import java.io.IOException
import java.lang.StringBuilder
import kotlin.properties.Delegates


class FirstFragment : Fragment() {
    val msklocation = LatLng(55.7558, 37.6173)
    lateinit var myLocation: LatLng
    var myExactLocation: LatLng = LatLng(55.7558, 37.6173)
    private lateinit var map: GoogleMap
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    private var PERMISSION_ID = 52

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (mapFragment as SupportMapFragment).getMapAsync { map ->
            this.map = map
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@getMapAsync
            }
            map.isMyLocationEnabled = true
            map.setOnMapLongClickListener { latlng ->
                myLocation = LatLng(latlng.latitude, latlng.longitude)
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_marker)
                val scaleBitmap = Bitmap.createScaledBitmap(bitmap,110,110, false)
                val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(scaleBitmap)
                getAddress(myLocation)
                val titleStr = getAddress(myLocation)
                val marker = MarkerOptions()
                    .position(myLocation)
                    .title(titleStr)
                    .draggable(true)
                    .icon(bitmapDescriptor)
                map.addMarker(marker)
                map.setOnMarkerClickListener { marker ->
                    marker.remove()
                    true

                }
            }
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_marker)
            val scaleBitmap = Bitmap.createScaledBitmap(bitmap,110,110, false)
            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(scaleBitmap)
            val marker = MarkerOptions()
                .position(myExactLocation)
                .title("Marker")
                .draggable(true)
                .icon(bitmapDescriptor)
            map.addMarker(marker)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(myExactLocation, 17f))
            map.setOnMarkerClickListener { marker ->
                true
            }
        }
    }

    private fun getAddress(latLng: LatLng): String {
        // 1
        val geocoder = Geocoder(requireContext())
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""
        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }
        return addressText
    }

    private fun CheckPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun RequestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity() as Activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)

        val titleStr = getAddress(location)
        markerOptions.title(titleStr)

        map.addMarker(markerOptions)
    }

//    private fun isLocationEnabled(): Boolean {
//        var locationManager: LocationManager =
//            getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER || locationManager.isProviderEnabled(
//                LocationManager.NETWORK_PROVIDER
//                )
//    }
}