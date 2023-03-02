package com.example.plantcare.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.plantcare.R
import com.example.plantcare.ui.AlarmActivity


fun Context.showNotificationWithFullScreenIntent(
    notificationID: Int? = 0,
    channelId: String = CHANNEL_ID,
    title: String? = "Watering time",
    category: String? = "",
    description: String = "Don't forget watering '${category + "s"}' "

) {
    val intent = Intent(this, AlarmActivity::class.java)
    val pendingIntent =
        PendingIntent.getActivity(this, notificationID!!, intent, PendingIntent.FLAG_ONE_SHOT)

    val builder = NotificationCompat.Builder(this, channelId).setSmallIcon(R.drawable.florist)
        .setContentTitle(title)
        .setContentText(description)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)


    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    with(notificationManager) {
        buildChannel()

        val notification = builder.build()

        notify(notificationID, notification)
    }

}

private fun NotificationManager.buildChannel() {
    val name = "Example Notification Channel"
    val descriptionText = "This is used to demonstrate the Full Screen Intent"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
        description = descriptionText
    }

    createNotificationChannel(channel)
}

private const val CHANNEL_ID = "channelId"
