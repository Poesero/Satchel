package com.example.satchelbooksharing.ui.satchel.navigation

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.satchelbooksharing.data.FirestoreLibraryRepository
import com.example.satchelbooksharing.data.LocalLibraryRepository
import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.ui.satchel.screens.BookScreen
import com.example.satchelbooksharing.ui.satchel.screens.HomeScreen
import com.example.satchelbooksharing.ui.satchel.screens.LibraryScreen
import com.example.satchelbooksharing.ui.satchel.screens.NewBookScreen
import com.example.satchelbooksharing.ui.satchel.screens.ProfileScreen
import com.example.satchelbooksharing.ui.satchel.screens.SearchScreen
import com.example.satchelbooksharing.viewModel.satchel.LibraryViewModel

fun NavGraphBuilder.appGraph(navController: NavController,libraryViewModel: LibraryViewModel){
    navigation(startDestination = ScreensRoute.ScreenHomeRoute.route, route = ScreensRoute.AppRoute.route){
        composable(route = ScreensRoute.ScreenHomeRoute.route){
            HomeScreen(navController = navController)
        }
        composable(route = ScreensRoute.ScreenSearchRoute.route){
            val books = libraryViewModel.books.collectAsState().value
            SearchScreen(navController = navController, allBooks = books)
        }
        composable(route = ScreensRoute.ScreenLibraryRoute.route){
            LibraryScreen(navController = navController, libraryViewModel = libraryViewModel)
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

    }
}