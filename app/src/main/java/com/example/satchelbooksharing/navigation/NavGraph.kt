package com.example.satchelbooksharing.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.satchelbooksharing.ui.satchel.screens.SplashScreen
import com.example.satchelbooksharing.viewModel.satchel.AuthViewModel
import com.example.satchelbooksharing.viewModel.satchel.LibraryViewModel
import com.example.satchelbooksharing.viewModel.satchel.MainViewModel

@Composable
fun SatchelNav(
    libraryViewModel: LibraryViewModel,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = ScreensRoute.ScreenSplashRoute.route
    ) {
        composable(ScreensRoute.ScreenSplashRoute.route) {
            val mainViewModel: MainViewModel = viewModel()
            SplashScreen(navController = navController, viewModel = mainViewModel)
        }

        authGraph(navController, authViewModel)
        appGraph(navController, libraryViewModel)
    }
}
