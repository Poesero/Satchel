package com.example.satchelbooksharing.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.satchelbooksharing.ui.satchel.screens.AuthScreen
import com.example.satchelbooksharing.viewModel.satchel.AuthViewModel

fun NavGraphBuilder.authGraph(navController: NavController, authViewModel: AuthViewModel){

    navigation(
        startDestination = ScreensRoute.ScreenAuthRoute.route,
        route = ScreensRoute.AuthRoute.route
    ) {
        composable(ScreensRoute.ScreenAuthRoute.route) {
            AuthScreen(viewModel = authViewModel, navController = navController)
        }
    }
}