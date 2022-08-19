package com.ilya.myapptracker.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Coordinate(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val latitude:Double,
    val longitude: Double,

    val runningDataId: String
): Parcelable

fun Coordinate.toLatLng(): LatLng{
    return LatLng(
        latitude,
        longitude
    )
}