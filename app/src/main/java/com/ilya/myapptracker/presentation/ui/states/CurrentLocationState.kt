package com.ilya.myapptracker.presentation.ui.states

import com.google.android.gms.maps.model.LatLng

data class CurrentLocationState(
    val data: LatLng = LatLng(0.0, 0.0),
    val isLoading: Boolean = false,
    val error: String =""
)