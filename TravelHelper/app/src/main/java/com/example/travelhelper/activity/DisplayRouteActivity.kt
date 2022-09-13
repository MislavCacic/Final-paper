package com.example.travelhelper.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.travelhelper.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.travelhelper.databinding.ActivityDisplayRouteBinding
import com.example.travelhelper.fragment.route.sights.ListOfSightsFragment
import com.example.travelhelper.model.Route
import com.google.android.gms.maps.model.LatLngBounds

class DisplayRouteActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val ROUTE = "route"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDisplayRouteBinding

    private lateinit var route: Route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        route = intent.getParcelableExtra(ROUTE)!!

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val boundsBuilder = LatLngBounds.Builder()
        route.sights.forEach {
            it.location?.let { coordinate ->
                val coordinates = LatLng(coordinate.latitude, coordinate.longitude)
                boundsBuilder.include(coordinates)
                mMap.addMarker(MarkerOptions().position(coordinates).title(it.name))
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 500, 500, 10))
    }
}