package com.stein.spacelunch.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stein.spacelunch.ui.upcoming_list.UpcomingListScreen

//todo add tests for navigation
@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { UpcomingListScreen(modifier = Modifier.padding(16.dp)) }
    }
}