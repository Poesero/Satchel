package com.example.satchelbooksharing.ui.satchel.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.satchelbooksharing.ui.satchel.screens.HomeScreen
import com.example.satchelbooksharing.ui.satchel.screens.LibraryScreen
import com.example.satchelbooksharing.ui.satchel.screens.ProfileScreen
import com.example.satchelbooksharing.ui.satchel.screens.SearchScreen

fun NavGraphBuilder.appGraph(navController: NavController){
    navigation(startDestination = ScreensRoute.ScreenHomeRoute.route, route = ScreensRoute.AppRoute.route){
        composable(route = ScreensRoute.ScreenHomeRoute.route){
            HomeScreen(navController = navController)
        }
        composable(route = ScreensRoute.ScreenSearchRoute.route){
            SearchScreen(navController = navController)
        }
        composable(route = ScreensRoute.ScreenLibraryRoute.route){
            LibraryScreen(navController = navController)
        }
        composable(route = ScreensRoute.ScreenProfileRoute.route){
            ProfileScreen(navController = navController)
        }
    }
}