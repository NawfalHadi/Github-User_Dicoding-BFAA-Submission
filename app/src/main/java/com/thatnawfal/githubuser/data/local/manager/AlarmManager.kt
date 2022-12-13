package com.thatnawfal.githubuser.data.local.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmManager : BroadcastReceiver() {

    companion object {
        const val TYPE_REPEATING = "RepeatingAlarm"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        private const val ID_REPEATING = 101
    }

    override fun onReceive(context: Context, intent: Intent) {

    }

}