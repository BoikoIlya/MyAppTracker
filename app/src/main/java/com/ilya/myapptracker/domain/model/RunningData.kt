package com.ilya.myapptracker.domain.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.ilya.myapptracker.utils.Constants
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = Constants.RUNNING_RESULTS_TABLE)
data class RunningData(
    @PrimaryKey(autoGenerate = false)
    val runningDataId: String,
    val dateRun: String,
    val distanceInMeters: Float =0f,
    val durationInSeconds: Long = 0L,
    val averageSpeedInKmH: Float = 0f,
    val calories: Int = 0
): Parcelable
