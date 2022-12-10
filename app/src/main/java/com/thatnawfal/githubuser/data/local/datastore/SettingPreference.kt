package com.thatnawfal.githubuser.data.local.datastore

import androidx.datastore.core.DataStore
import java.util.prefs.Preferences

class SettingPreference private constructor(private val datastore: DataStore<Preferences>){
}