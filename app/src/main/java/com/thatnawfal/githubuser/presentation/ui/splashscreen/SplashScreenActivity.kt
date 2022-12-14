package com.thatnawfal.githubuser.presentation.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.di.ServiceLocator
import com.thatnawfal.githubuser.presentation.logic.SettingsViewModel
import com.thatnawfal.githubuser.presentation.ui.MainActivity
import com.thatnawfal.githubuser.utils.viewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val settingViewModel by viewModelFactory {
        SettingsViewModel(ServiceLocator.provideSettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        settingViewModel.getThemes().observe(this){
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, LOADING_TIME)
    }

    companion object {
        const val LOADING_TIME = 3000L
    }
}