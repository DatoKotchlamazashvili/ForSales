package com.example.tbcexercises.main_activity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
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
import com.example.tbcexercises.utils.popUpMenuHelper
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

        setupLanguageMenu()


    }

    private fun observers(navController: NavController) {
        runBlocking {
            val rememberMe = viewModel.rememberMe.first()
            Log.d("rememberME",rememberMe.toString())
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
                binding.languageIcon.setImageResource(languages.first {
                    it.name == language
                }.flag)
            }
        }
    }

    private fun showViews(
        bottomNav: Boolean,
        appBar: Boolean = true,
        appBarTitle: String = getString(R.string.app_nickname)
    ) {
        binding.apply {
            bottomNavigationView.isVisible = bottomNav
            topAppBar.isVisible = appBar
            topAppBar.title = appBarTitle
        }

    }

    private fun setUpBottomBarAndAppBar(navController: NavController) {
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> showViews(
                    bottomNav = false,
                    appBarTitle = getString(R.string.login)
                )

                R.id.registerFragment -> showViews(
                    bottomNav = false,
                    appBarTitle = getString(R.string.register)
                )

                else -> showViews(bottomNav = true)
            }
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
                    viewModel.setSession(selectedLanguage, null)
                }
                true
            }
            popup.show()

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
