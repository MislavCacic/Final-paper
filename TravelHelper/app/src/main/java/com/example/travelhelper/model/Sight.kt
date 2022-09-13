package com.example.travelhelper.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sight(
    var id: String? = null,
    val name: String? = "",
    val description: String? = "",
    val location: SightLocation? = null,
    val architecture: String? = "",
    val rating: Int = 5
): Parcelable