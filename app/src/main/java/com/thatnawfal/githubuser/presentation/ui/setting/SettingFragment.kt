package com.thatnawfal.githubuser.presentation.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.thatnawfal.githubuser.utils.TimePickerFragment
import com.thatnawfal.githubuser.databinding.FragmentSettingBinding
import com.thatnawfal.githubuser.di.ServiceLocator
import com.thatnawfal.githubuser.presentation.logic.SettingsViewModel
import com.thatnawfal.githubuser.utils.viewModelFactory

class SettingFragment : Fragment() {

    private lateinit var binding : FragmentSettingBinding
    private val settingViewModel by viewModelFactory {
        SettingsViewModel(ServiceLocator.provideSettingPreferences(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEdit.setOnClickListener {
            val timePickerFragmentRepeat = TimePickerFragment()
            timePickerFragmentRepeat.show(parentFragmentManager , TIME_PICKER_REPEAT_TAG)
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

    companion object {
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }
}