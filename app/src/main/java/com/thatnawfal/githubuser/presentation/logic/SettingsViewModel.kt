package com.thatnawfal.githubuser.presentation.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thatnawfal.githubuser.data.local.datastore.SettingPreference
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingPreference: SettingPreference
) : ViewModel() {

    fun getThemes() : LiveData<Boolean>{
        return settingPreference.getTheme().asLiveData()
    }

    fun setThemes(theme: Boolean) {
        viewModelScope.launch {
            settingPreference.changeTheme(theme)
        }
    }
}