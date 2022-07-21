package com.example.meteo.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.example.meteo.R
import com.example.meteo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null
    private var navGraph: NavGraph? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment).navController
        navGraph = navController?.navInflater?.inflate(R.navigation.weather_graph)
        navGraph?.also { navController?.setGraph(it, intent.extras) }
    }

    override fun onBackPressed() {
        if (navController?.popBackStack() == true) {
            return
        }

        finish()
    }
}