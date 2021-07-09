package com.example.android.app.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.github.droibit.komol.Komol

fun NavController.navigateSafely(directions: NavDirections) {
    if (currentDestination?.getAction(directions.actionId) == null) {
        Komol.w("Action corresponding to Directions($directions) could not be found.")
        return
    }
    navigate(directions)
}