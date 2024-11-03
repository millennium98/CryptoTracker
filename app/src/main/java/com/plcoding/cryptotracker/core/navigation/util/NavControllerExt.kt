package com.plcoding.cryptotracker.core.navigation.util

import androidx.navigation.NavController
import androidx.navigation.navOptions

fun <T: Any> NavController.popAndNavigate(
    route: T
) {
    navigate(
        route = route,
        navOptions = navOptions {
            val currentRoute = currentDestination?.route ?: return@navOptions
            popUpTo(currentRoute) {
                inclusive = true
            }
        }
    )
}