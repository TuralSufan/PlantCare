package com.example.plantcare.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class MyBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action.equals("com.plant.alarmmanager")) {
            val bundle = intent.extras
            context?.showNotificationWithFullScreenIntent(
                category = bundle?.getString("category"),
                notificationID = bundle?.getInt("alarmID")
            )
        }
    }
}
