package com.schutz.stock

import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.schutz.stock.databinding.ActivityMainBinding
import com.schutz.stock.service.DatabaseClient
import java.io.File
import java.io.FileOutputStream
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseClient: DatabaseClient
    private val REQUEST_CODE: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Locale.setDefault(Locale.FRANCE)

/*
        if (ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.MANAGE_EXTERNAL_STORAGE),
                REQUEST_CODE)
        } else {
            // Permissions already granted
            databaseClient = DatabaseClient.initInstance(baseContext)
        }
*/


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


/*
        return if (uri != null) {
            resolver.openOutputStream(uri)?.use { outputStream ->
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "MyAppDatabase/$fileName")
                FileOutputStream(file).use { fileOutputStream ->
                    outputStream.copyTo(fileOutputStream)
                }
                file
            }
        } else {
            null
        }
    }
*/

 /*   override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                databaseClient = DatabaseClient.initInstance(baseContext)
            } else {
                // Permission denied
            }
        }
    }
*/
}