package com.example.tbcexercises.main_activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ActivityMainBinding
import com.example.tbcexercises.domain.model.Language
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

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
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fvcNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        setupLanguageSpinner()

        lifecycleScope.launch {
            viewModel.languagePreference.collectLatest { language ->
                loadLocale(language)
            }
        }
    }

    private fun setupLanguageSpinner() {
        val languages = listOf(
            Language("en", R.drawable.ic_flag_english),
            Language("ka", R.drawable.iic_flag_georgia)
        )

        val adapter = LanguageSpinnerAdapter(this, languages)
        binding.languageSpinner.adapter = adapter

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

                    viewModel.setSession(selectedLanguage)
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

            //createConfigurationContext(config)
            viewModel.setSession(language)

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

        //createConfigurationContext(config)
    }



}