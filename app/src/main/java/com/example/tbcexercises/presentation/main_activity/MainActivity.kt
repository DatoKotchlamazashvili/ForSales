package com.example.tbcexercises.presentation.main_activity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ActivityMainBinding
import com.example.tbcexercises.utils.Constants.languages
import com.example.tbcexercises.utils.popUpMenuHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
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

        setupNavigation()
        observeLanguageChanges()
        setupLanguageMenu()

    }


    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fvcNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)
        setupBottomNavBehavior()
        setupDestinationChangedListener()
    }

    private fun setupBottomNavBehavior() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == navController.currentDestination?.id) {
                return@setOnItemSelectedListener false
            }

            val navOptions = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(menuItem.itemId, inclusive = true)
                .build()

            navController.navigate(menuItem.itemId, null, navOptions)
            true
        }
    }

    private fun setupDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> configureViewsForDestination(
                    bottomNav = false,
                    appBarTitle = getString(R.string.login),
                    languageIconIsVisible = true
                )

                R.id.registerFragment -> configureViewsForDestination(
                    bottomNav = false,
                    appBarTitle = getString(R.string.register),
                    languageIconIsVisible = true
                )

                R.id.launcherFragment -> configureViewsForDestination(
                    bottomNav = false,
                    appBar = false
                )

                R.id.profileFragment -> configureViewsForDestination(
                    bottomNav = true,
                    languageIconIsVisible = true
                )

                else -> configureViewsForDestination(bottomNav = true)
            }
            binding.bottomNavigationView.menu.findItem(destination.id)?.isChecked = true
        }
    }

    private fun observeLanguageChanges() {
        lifecycleScope.launch {
            viewModel.languagePreference.collect { language ->
                loadLocale(language)
                updateLanguageIcon(language)
            }
        }
    }

    private fun updateLanguageIcon(language: String) {
        binding.languageIcon.setImageResource(languages.first {
            it.name == language
        }.flag)
    }

    private fun configureViewsForDestination(
        bottomNav: Boolean,
        appBar: Boolean = true,
        appBarTitle: String = getString(R.string.app_nickname),
        languageIconIsVisible: Boolean = false
    ) {
        binding.apply {
            bottomNavigationView.isVisible = bottomNav
            topAppBar.isVisible = appBar
            topAppBar.title = appBarTitle
            languageIcon.isVisible = languageIconIsVisible
        }
    }

    private fun setupLanguageMenu() {
        binding.languageIcon.setOnClickListener { view ->
            val popup = PopupMenu(ContextThemeWrapper(this, R.style.CustomPopupMenu), view)
            languages.forEachIndexed { index, language ->
                popup.menu.add(0, index, index, language.name).setIcon(language.flag)
            }
            popUpMenuHelper(popup)

            popup.setOnMenuItemClickListener { item ->
                val selectedLanguage = languages[item.itemId].name
                Log.d("languageSelected", selectedLanguage)
                if (resources.configuration.locales[0].language != selectedLanguage) {
                    viewModel.setLanguage(selectedLanguage)
                }
                true
            }
            popup.show()
        }
    }

    private fun loadLocale(language: String) {
        val currentLanguage = resources.configuration.locales[0].language
        if (currentLanguage != language) {
            applyLanguageConfiguration(language)
            viewModel.setLanguage(language)
            recreate()
        }
    }

    private fun applyLanguageConfiguration(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun applySavedLocale() {
        val language = runBlocking {
            viewModel.languagePreference.first()
        }
        applyLanguageConfiguration(language)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.logout()
    }
}