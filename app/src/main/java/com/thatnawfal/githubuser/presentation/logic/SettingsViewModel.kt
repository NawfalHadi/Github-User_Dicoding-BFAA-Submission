package com.thatnawfal.githubuser.presentation.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.thatnawfal.githubuser.data.local.datastore.SettingPreference

class SettingsViewModel(
    private val settingPreference: SettingPreference
) : ViewModel() {

    fun getThemes() : LiveData<Boolean>{
        return settingPreference.getTheme().asLiveData()
    }
}