package com.example.satchelbooksharing.ui.satchel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.satchelbooksharing.ui.satchel.screens.BookScreen
import com.example.satchelbooksharing.ui.satchel.screens.ChatScreen
import com.example.satchelbooksharing.ui.satchel.screens.HomeScreen
import com.example.satchelbooksharing.ui.satchel.screens.LibraryScreen
import com.example.satchelbooksharing.ui.satchel.screens.ProfileScreen
import com.example.satchelbooksharing.ui.satchel.screens.SearchScreen
import com.example.satchelbooksharing.ui.satchel.screens.SplashScreen

@Composable
fun SatchelNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ){
        composable("splash"){
            SplashScreen(navController)
        }
        composable("home"){
            HomeScreen()
        }
        composable("library"){
            LibraryScreen()
        }
        composable("profile"){
            ProfileScreen()
        }
        composable("search"){
            SearchScreen()
        }
        composable("Book"){
            BookScreen()
        }
        composable("Chat"){
            ChatScreen()
        }
    }
}