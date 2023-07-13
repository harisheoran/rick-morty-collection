package com.example.rickmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickmorty.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        // navhostfragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        // appBarConfiguration take navgraph to manage behaviour of up button in navigation button
        // as navigation behaviour changes weather the user is on top level destination or not
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.characterListFragment, R.id.episodeListFragment)
        )

        setupActionBarWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )

    }


    // support back navigation
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}