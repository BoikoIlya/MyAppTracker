package com.ilya.myapptracker.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.graphics.toColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ilya.myapptracker.R
import com.ilya.myapptracker.presentation.ui.activities.MainActivity
import com.ilya.myapptracker.presentation.ui.activities.TrackingActivity
import com.ilya.myapptracker.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
class ServiceModule {

    @OptIn(ExperimentalPermissionsApi::class)
    @ServiceScoped
    @Provides
    fun provideMainActivityPendingIntent(
        @ApplicationContext app: Context
    ) = PendingIntent.getActivity(
        app,
        0,
        Intent(app, TrackingActivity::class.java).also {
            it.action = Constants.ACTION_SHOW_TRACKING_FRAGMENT
        },
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext app: Context,
        pendingIntent: PendingIntent
    ) = NotificationCompat.Builder(app, Constants.NOTIFICATION_CHANNEL_ID)
        .setAutoCancel(false)
        .setOngoing(true)
        .setSmallIcon(R.drawable.shoes_white)
        .setContentTitle("Tracking")
        .setContentText("00:00:00")
        .setContentIntent(pendingIntent)
}