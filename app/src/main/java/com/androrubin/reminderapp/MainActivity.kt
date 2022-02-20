package com.androrubin.reminderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.androrubin.reminderapp.databinding.ActivityMainBinding
import com.androrubin.reminderapp.databinding.FragmentPomodoroBinding
import com.androrubin.reminderapp.util.NotificationUtil
import com.androrubin.reminderapp.util.PrefUtil
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController=findNavController(R.id.fragmentContainerView)
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController=navController)
        val appBarConfiguration= AppBarConfiguration(setOf(R.id.homeFragment,R.id.pomodoroFragment,R.id.communityFragment,R.id.profileFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)


    }




}