package com.example.plantcare.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.plantcare.model.db.AlarmDAO
import com.example.plantcare.model.db.PlantDatabase
import java.util.*

class SaveAlarmData {

    var context: Context? = null
    private var dao: AlarmDAO? = null

    constructor(context: Context) {
        this.context = context
        dao = PlantDatabase.invoke(context).getAlarmDAO()
    }


    fun setAlarm(alarmID: Int) {
        val hour: Int? = dao?.getAlarmByID(alarmID)?.hour
        val minute: Int? = dao?.getAlarmByID(alarmID)?.minute
        val category: String? = dao?.getAlarmByID(alarmID)?.category
        val repeatDay: Int? = dao?.getAlarmByID(alarmID)?.repeatDay
        val isAlarmActive: Boolean = dao?.getAlarmByID(alarmID)!!.isActive
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour!!)
        calendar.set(Calendar.MINUTE, minute!!)
        calendar.set(Calendar.SECOND, 0)


        val now = Calendar.getInstance()
        if (now.after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val intent = Intent(context, MyBroadcastReceiver::class.java)
        intent.putExtra("category", category)
        intent.putExtra("alarmID", alarmID)
        intent.action = "com.plant.alarmmanager"
        val pi =
            PendingIntent.getBroadcast(context, alarmID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        when (isAlarmActive) {
            true -> if (repeatDay != null && repeatDay > 0) {
                val interval = AlarmManager.INTERVAL_DAY * repeatDay
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis, interval, pi
                )
            } else {
                alarmManager.setAlarmClock(
                    AlarmManager.AlarmClockInfo(calendar.timeInMillis, pi), pi
                )

            }
            false -> alarmManager.cancel(pi)
        }

    }

    fun cancelAlarm(alarmID: Int) {
        val hour: Int? = dao?.getAlarmByID(alarmID)?.hour
        val minute: Int? = dao?.getAlarmByID(alarmID)?.minute
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour!!)
        calendar.set(Calendar.MINUTE, minute!!)
        calendar.set(Calendar.SECOND, 0)

        val intent = Intent(context, MyBroadcastReceiver::class.java)
        intent.action = "com.plant.alarmmanager"
        val pi =
            PendingIntent.getBroadcast(context, alarmID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        alarmManager.cancel(pi)

    }


}
