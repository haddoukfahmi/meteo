package com.example.meteo.extension

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment

fun Fragment.navigateTo(navDirections: NavDirections?) {
    with(NavHostFragment.findNavController(this)) {
        navDirections?.actionId?.also { actionId ->
            currentDestination?.getAction(actionId)?.also {
                navigate(navDirections)
            }
        }
    }
}