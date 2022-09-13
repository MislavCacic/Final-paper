package com.example.travelhelper.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Route(
    val sights: List<Sight>
) : Parcelable