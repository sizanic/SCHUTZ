package com.schutz.stock

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.schutz.stock.databinding.ActivityMainBinding
import com.schutz.stock.service.DatabaseClient
import java.util.Locale



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseClient: DatabaseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Locale.setDefault(Locale.FRANCE)

        databaseClient = DatabaseClient.initInstance(baseContext)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root

        setContentView(view)

        view.windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())

        // https://medium.com/@mr.appbuilder/navigating-android-fragments-with-the-navigation-component-part-1-1d238e000313
        // https://stackoverflow.com/questions/59275009/fragmentcontainerview-using-findnavcontroller/59275182#59275182

        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        val navHome = view.findViewById<Button>(R.id.button_home)
        navHome.setOnClickListener { navController.navigate(R.id.navigation_home) }

        val navAdd = view.findViewById<Button>(R.id.button_add)
        navAdd.setOnClickListener { navController.navigate(R.id.navigation_add) }

        val navRemove = view.findViewById<Button>(R.id.button_remove)
        navRemove.setOnClickListener { navController.navigate(R.id.navigation_remove) }

        val navSearch = view.findViewById<Button>(R.id.button_search)
        navSearch.setOnClickListener { navController.navigate(R.id.navigation_search) }


        navView.setupWithNavController(navController)
    }



}