package com.example.rickmorty

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // navhostfragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        // appBarConfiguration take navgraph to manage behaviour of up button in navigation button
        // as navigation behaviour changes weather the user is on top level destination or not
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.characterListFragment, R.id.episodeListFragment),
            drawerLayout = drawerLayout,
        )

        setupActionBarWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)
        findViewById<NavigationView>(R.id.nav_view).setCheckedItem(
            navController.graph.startDestinationId
        )


        // about dev menu item
        val menu = findViewById<NavigationView>(R.id.nav_view).menu
        val aboutDev = menu.findItem(R.id.about_dev)
        aboutDev?.setOnMenuItemClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://harisheoran.github.io"))
            startActivity(intent)
            true
        }
    }



    // support back navigation
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}