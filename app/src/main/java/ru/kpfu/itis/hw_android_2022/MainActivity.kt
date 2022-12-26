package ru.kpfu.itis.hw_android_2022

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.kpfu.itis.hw_android_2022.databinding.ActivityMainBinding
import ru.kpfu.itis.hw_android_2022.db.DatabaseHandler
import ru.kpfu.itis.hw_android_2022.utils.PermissionsHandler
import ru.kpfu.itis.hw_android_2022.utils.PreferencesHandler

class MainActivity : AppCompatActivity() {

    // можно и без lateinit или, возможно, с делегатами?
    // всегда ориентировался на эти ссылки
    // https://developer.android.com/topic/libraries/view-binding
    // https://medium.com/mobile-app-development-publication/not-all-viewbinding-need-null-setting-e16fe6737489

    private var _binding: ActivityMainBinding? = null
    private val binding by lazy { _binding!! }

    // несмотря на то, что read/write external storage является runtime permission
    // разрешения не требуются для работы room. Я не понял почему и ответа не нашёл

    private var requestPermission = PermissionsHandler(this) {

    }
    private var preferencesHandler: PreferencesHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseHandler.initializeDb(applicationContext)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencesHandler = PreferencesHandler(this)
        initNavComponent()

        // navigateToAuth()
        // navigateToMain()
    }

    private fun initNavComponent() {
        val controller =
            (supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment).navController
        val bottomView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomView.setupWithNavController(controller)

        with(binding) {
            bottomNavigationView.setupWithNavController(controller)
        }
        controller.addOnDestinationChangedListener { _, destination, _ ->

            //достаточно проверять только основной фрагмент,
            // потому что мы по логике вещей внезапно в настройки не можем перейти
            if (destination.id == R.id.profileFragment) {
                if (checkIfUserIsLoggedIn()) {
                    binding.bottomNavigationView.visibility = ViewGroup.VISIBLE
                } else {
                    controller.navigate(
                        R.id.action_profileFragment_to_loginFragment
                    )
                    binding.bottomNavigationView.visibility = ViewGroup.GONE
                }
            }
        }
    }



    private fun checkIfUserIsLoggedIn(): Boolean =
        preferencesHandler?.getUsername() != "" && preferencesHandler?.getUsername() != null


    /*
    private fun navigateToAuth() {
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.auth_container) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

     */

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        preferencesHandler = null
    }
}