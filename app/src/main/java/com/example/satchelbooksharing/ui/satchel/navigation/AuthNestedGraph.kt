package com.example.satchelbooksharing.ui.satchel.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.satchelbooksharing.ui.satchel.screens.SplashScreen

fun NavGraphBuilder.authGraph(navController: NavController){

    navigation(startDestination = ScreensRoute.ScreenSplashRoute.route, route = ScreensRoute.AuthRoute.route) {
        composable(route = ScreensRoute.ScreenSplashRoute.route) {
            SplashScreen(navController = navController)
        }
    }
}