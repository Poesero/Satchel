package com.example.satchelbooksharing.ui.satchel.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
fun SatchelNavGraph(navController: NavHostController,
                    showFooter: MutableState<Boolean>) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ){
        composable("splash"){
            showFooter.value = false
            SplashScreen(navController)
        }
        composable("home"){
            showFooter.value = true
            HomeScreen()
        }
        composable("library"){
            showFooter.value = true
            LibraryScreen()
        }
        composable("profile"){
            ProfileScreen()
        }
        composable("search"){
            showFooter.value = true
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