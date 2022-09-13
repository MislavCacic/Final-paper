package com.example.travelhelper.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.example.travelhelper.model.SightLocation

object LocationProvider {

    fun getLocationFromAddress(
        context: Context,
        address: String,
        onSuccess: (location: SightLocation) -> Unit
    ) {
        val list = address.split(",")

        val geocoder = Geocoder(context)
        val addressList: List<Address>

        try {
            addressList = geocoder.getFromLocationName(address, 1)

            if (addressList != null) {
                val lat = addressList[0].latitude
                val long = addressList[0].longitude

                onSuccess(
                    SightLocation(
                        address = list[0].trim(),
                        postalCode = list[1].trim(),
                        city = list[2].trim(),
                        country = list[3].trim(),
                        latitude = lat,
                        longitude = long
                    )
                )
            }
        } catch (ex: Exception) {
            Log.d("LocationProvider", "Failed to fetch address: $ex")
        }
    }
}