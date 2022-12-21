package com.arthur.test.emovie.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.navOptions
import com.arthur.test.emovie.R

/**
 * Navigate to next destination fragment checking double click
 */
fun NavController.navigateToNext(currentDirection: Int, direction: NavDirections?) {
    if (this.currentDestination?.id == currentDirection) {
        direction?.let {
            this.navigate(it, getDefaultNavOptions())
        }
    }
}

/**
 * Navigation components animations
 */
fun getDefaultNavOptions() = navOptions {
    anim {
        enter = R.anim.slide_in_right
        exit = R.anim.slide_out_left
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }
}