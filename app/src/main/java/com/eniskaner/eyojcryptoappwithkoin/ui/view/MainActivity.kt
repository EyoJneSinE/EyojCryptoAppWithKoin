package com.eniskaner.eyojcryptoappwithkoin.ui.view

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.eniskaner.eyojcryptoappwithkoin.R

class MainActivity : AppCompatActivity() {
    private val navController: NavController by lazy {
        findNavController(R.id.fragmentContainerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.cryptoDetailFragment) {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            if (currentFragment is CryptoDetailFragment) {
                currentFragment.refreshData()
            }
        } else {
            super.onBackPressed()
        }
    }
}