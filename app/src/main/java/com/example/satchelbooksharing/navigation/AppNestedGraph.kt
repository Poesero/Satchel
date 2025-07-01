package com.example.satchelbooksharing.navigation

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

import com.example.satchelbooksharing.ui.satchel.screens.BookScreen
import com.example.satchelbooksharing.ui.satchel.screens.ChatScreen
import com.example.satchelbooksharing.ui.satchel.screens.HomeScreen
import com.example.satchelbooksharing.ui.satchel.screens.LibraryScreen
import com.example.satchelbooksharing.ui.satchel.screens.NewBookScreen
import com.example.satchelbooksharing.ui.satchel.screens.ProfileScreen
import com.example.satchelbooksharing.ui.satchel.screens.SearchScreen
import com.example.satchelbooksharing.ui.satchel.screens.UserLibraryScreen
import com.example.satchelbooksharing.viewModel.satchel.LibraryViewModel

fun NavGraphBuilder.appGraph(navController: NavController,libraryViewModel: LibraryViewModel){
    navigation(startDestination = ScreensRoute.ScreenHomeRoute.route, route = ScreensRoute.AppRoute.route){
        composable(route = ScreensRoute.ScreenHomeRoute.route){
            HomeScreen(navController = navController)
        }
        composable(route = ScreensRoute.ScreenSearchRoute.route){
            val books = libraryViewModel.allBooks.collectAsState().value
            SearchScreen(navController = navController, allBooks = books)
        }
        composable(route = ScreensRoute.ScreenLibraryRoute.route){
            LibraryScreen(navController = navController, libraryViewModel = libraryViewModel)
        }
        composable(
            route = "UserLibrary/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = requireNotNull(backStackEntry.arguments?.getString("userId"))
            UserLibraryScreen(navController = navController, userId = userId)
        }
        composable(route = ScreensRoute.ScreenProfileRoute.route){
            ProfileScreen(navController = navController)
        }
        composable(route = ScreensRoute.ScreenNewBookRoute.route){
            NewBookScreen(navController = navController, libraryViewModel = libraryViewModel)
        }
        composable(
            route = "${ScreensRoute.ScreenBookRoute.route}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
            BookScreen(navController = navController, bookId = bookId)
        }
        composable(
            route = "chat_screen/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: return@composable
            ChatScreen(navController = navController, chatId = chatId)
        }

    }
}