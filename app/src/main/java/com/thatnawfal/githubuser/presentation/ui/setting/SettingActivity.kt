package com.thatnawfal.githubuser.presentation.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.databinding.ActivitySettingBinding
import com.thatnawfal.githubuser.databinding.FragmentSettingBinding
import com.thatnawfal.githubuser.di.ServiceLocator
import com.thatnawfal.githubuser.presentation.logic.SettingsViewModel
import com.thatnawfal.githubuser.utils.TimePickerFragment
import com.thatnawfal.githubuser.utils.viewModelFactory

class SettingActivity : AppCompatActivity() {

    companion object {
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    private lateinit var binding : ActivitySettingBinding
    private val settingViewModel by viewModelFactory {
        SettingsViewModel(ServiceLocator.provideSettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEdit.setOnClickListener {
            val timePickerFragmentRepeat = TimePickerFragment()
            timePickerFragmentRepeat.show(supportFragmentManager ,
                TIME_PICKER_REPEAT_TAG
            )
        }

        themeChecker()
    }

    private fun themeChecker() {
        binding.switchTheme.setOnCheckedChangeListener { _ , isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
                settingViewModel.setThemes(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
                settingViewModel.setThemes(false)
            }
        }
    }
}