package com.example.tbcexercises.main_activity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ActivityMainBinding
import com.example.tbcexercises.utils.Constants.languages
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var keepSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplash }

        super.onCreate(savedInstanceState)
        applySavedLocale()

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fvcNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        observers(navController)
        setUpBottomBarAndAppBar(navController)

        setupLanguageSpinner()


    }

    private fun observers(navController: NavController) {
        lifecycleScope.launch {
            val rememberMe = viewModel.rememberMe.first()
            delay(1000L)
            if (rememberMe) {
                navController.navigate(R.id.homeFragment)
            } else {
                navController.navigate(R.id.navigation)
            }
            keepSplash = false
        }

        lifecycleScope.launch {
            viewModel.languagePreference.collectLatest { language ->
                loadLocale(language)
            }
        }
    }

    private fun showViews(bottomNav: Boolean, appBar: Boolean = true) {
        binding.apply {
            bottomNavigationView.isVisible = bottomNav
            topAppBar.isVisible = appBar
        }
    }

    private fun setUpBottomBarAndAppBar(navController: NavController) {
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> showViews(bottomNav = false)
                R.id.registerFragment -> showViews(bottomNav = false)
                else -> showViews(bottomNav = true)
            }
        }
    }

    private fun setupLanguageSpinner() {


        val adapter = LanguageSpinnerAdapter(this, languages)
        binding.languageSpinner.adapter = adapter

        lifecycleScope.launch {
            val savedLanguage = viewModel.languagePreference.first()
            val selectedIndex = languages.indexOfFirst { it.name == savedLanguage }
            if (selectedIndex >= 0) {
                binding.languageSpinner.setSelection(selectedIndex)
            }
        }

        binding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedLanguage = languages[position].name
                    Log.d("languageSelected", selectedLanguage)
                    if (resources.configuration.locales[0].language != selectedLanguage) {
                        viewModel.setSession(selectedLanguage, null)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun loadLocale(language: String) {
        val currentLanguage = resources.configuration.locales[0].language
        Log.d("languagold", currentLanguage)
        Log.d("languageNew", language)
        if (currentLanguage != language) {
            Log.d("language", "executed")
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)

            viewModel.setSession(language, null)
            recreate()
        }
    }

    private fun applySavedLocale() {
        val language = runBlocking {
            viewModel.languagePreference.first()
        }

        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.logout()
    }
}
