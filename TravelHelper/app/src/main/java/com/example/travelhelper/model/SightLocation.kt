package com.example.travelhelper.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SightLocation(
    val address: String? = null,
    val city: String? = null,
    val postalCode: String? = null,
    val country: String? = null,
    val longitude: Double = 0.0,
    val latitude: Double = 0.0
): Parcelable {
    fun getLocation(): String = "$address, $city, $postalCode, $country"
}