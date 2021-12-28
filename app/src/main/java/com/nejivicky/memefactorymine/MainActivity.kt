package com.nejivicky.memefactorymine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.nejivicky.memefactorymine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController=navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragmentTest,R.id.savedFragment),
            binding.drawerLayout
        )
        setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(navController,appBarConfiguration)
        //binding.toolbar.setupWithNavController(navController,appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)
        //NavigationUI.setupWithNavController(binding.navigationView,navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)|| super.onSupportNavigateUp()
    }


    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }


    }

}