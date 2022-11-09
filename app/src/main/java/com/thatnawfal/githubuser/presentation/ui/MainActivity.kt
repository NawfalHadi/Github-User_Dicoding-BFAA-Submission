package com.thatnawfal.githubuser.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.data.model.UserModel
import com.thatnawfal.githubuser.databinding.ActivityMainBinding
import com.thatnawfal.githubuser.presentation.ui.home.adapter.UserAdapter
import com.thatnawfal.githubuser.presentation.ui.profile.DetailUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navContainer)
                as NavHostFragment

        navController = navHostFragment.navController
    }





}