package com.thatnawfal.githubuser.presentation.ui.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.thatnawfal.githubuser.databinding.ActivitySettingBinding
import com.thatnawfal.githubuser.di.ServiceLocator
import com.thatnawfal.githubuser.presentation.logic.SettingsViewModel
import com.thatnawfal.githubuser.utils.AlarmReceiver
import com.thatnawfal.githubuser.utils.TimePickerFragment
import com.thatnawfal.githubuser.utils.viewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class SettingActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var oldTimes: String
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var binding : ActivitySettingBinding
    private val settingViewModel by viewModelFactory {
        SettingsViewModel(ServiceLocator.provideSettingPreferences(this))
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        oldTimes = binding.tvAlarm.text.toString()

        binding.btnEdit.setOnClickListener {
            val timePickerFragmentRepeat = TimePickerFragment()
            timePickerFragmentRepeat.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
        }

        alarmReceiver = AlarmReceiver()

        settingViewModel.getAlarm().observe(this){
            binding.tvAlarm.text = it
            if (it == "00"){
                alarmReceiver.setRepeatingAlarm(this,
                    "06:00", "Submission Reminder")
                binding.tvAlarm.text = "06:00"
                settingViewModel.setAlarm("06:00")
            }
        }
        themeChecker()
    }

    private fun alarmChecking() {
        val times = binding.tvAlarm.text.toString()
        if (oldTimes != times) {
            binding.btnChange.visibility = View.VISIBLE
            binding.btnChange.setOnClickListener {
                alarmReceiver.setRepeatingAlarm(this,
                    times, "Submission Reminder")

                binding.btnChange.visibility = View.GONE
                settingViewModel.setAlarm(times)
            }
        }

    }

    private fun themeChecker() {
        settingViewModel.getThemes().observe(this){ isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _ , isChecked ->
            settingViewModel.setThemes(isChecked)
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            TIME_PICKER_REPEAT_TAG -> {
                binding.tvAlarm.text = dateFormat.format(calendar.time)
                alarmChecking()
            }
            else -> {}
        }
    }

    companion object {
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }
}