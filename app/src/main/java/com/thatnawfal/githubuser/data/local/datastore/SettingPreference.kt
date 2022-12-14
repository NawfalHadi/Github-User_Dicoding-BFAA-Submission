package com.thatnawfal.githubuser.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences

class SettingPreference(private val ctx : Context){

    suspend fun changeTheme(theme: Boolean){
        ctx.settingDataStore.edit {
            // true for dark, false for light
            it[darkmode] = theme
        }
    }

    suspend fun changeAlarm(times: String){
        ctx.settingDataStore.edit {
            it[alarm] = times
        }
    }

    fun getTheme() : Flow<Boolean>{
        return ctx.settingDataStore.data.map {
            it[darkmode] ?: false
        }
    }

    fun getAlarm() : Flow<String>{
        return ctx.settingDataStore.data.map {
            it[alarm] ?: "00"
        }
    }

    companion object {
        private const val DATASTORE_NAME = "settong_preference"

        private val darkmode = booleanPreferencesKey("darkmode_key")
        private val alarm = stringPreferencesKey("alarm_key")

        private val Context.settingDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}