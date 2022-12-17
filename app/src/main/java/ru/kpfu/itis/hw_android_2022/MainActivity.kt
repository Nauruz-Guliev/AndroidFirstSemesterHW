package ru.kpfu.itis.hw_android_2022

import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.kpfu.itis.hw_android_2022.databinding.ActivityAuthBinding
import ru.kpfu.itis.hw_android_2022.databinding.ActivityMainBinding
import ru.kpfu.itis.hw_android_2022.db.DatabaseHandler
import ru.kpfu.itis.hw_android_2022.utils.PermissionsHandler
import java.security.Permission

class MainActivity : AppCompatActivity() {
    private var _binding: ViewBinding? = null
    private val binding by lazy { _binding!! }

    private val requestPermission = PermissionsHandler(this) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseHandler.initializeDb(applicationContext)
        navigateToAuth()
    }

    private fun navigateToMain() {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val controller = (supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment).navController
        val bottomView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomView.setupWithNavController(controller)
        with(binding as ActivityMainBinding) {
            bottomNavigationView.setupWithNavController(controller)
        }
    }

    private fun navigateToAuth() {
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.auth_container) as NavHostFragment
        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }
}