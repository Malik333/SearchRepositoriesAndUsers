package com.malikbisic.searchrepositoriesandusers.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.malikbisic.searchrepositoriesandusers.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this,navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.v("MainActivity", "data: " + intent?.data)
        val navController = this.findNavController(R.id.nav_host_fragment)
        navController.handleDeepLink(intent)
    }
}