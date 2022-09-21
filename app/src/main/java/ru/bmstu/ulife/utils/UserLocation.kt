package ru.bmstu.ulife.utils

import com.google.android.gms.maps.model.LatLng

data class UserLocation(
    val latLng: LatLng,
    val isFromGps: Boolean,
)
