package com.ilya.myapptracker.data.local.db

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.ilya.myapptracker.domain.model.Coordinate
import com.ilya.myapptracker.domain.model.RunningData
import kotlinx.parcelize.Parcelize

@Parcelize
data class RunningDataAndCoordinates(

    @Embedded  val runningData: RunningData,
    @Relation(
                parentColumn = "runningDataId",
                entityColumn = "runningDataId"
            )
    val coordinates: List<Coordinate>
): Parcelable