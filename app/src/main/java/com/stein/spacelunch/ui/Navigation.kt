package com.stein.spacelunch.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stein.spacelunch.ui.upcoming_list.ui.UpcomingListScreen

//todo add tests for navigation
@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { UpcomingListScreen() }
    }
}