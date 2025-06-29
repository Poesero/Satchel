package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.satchelbooksharing.navigation.ScreensRoute
import com.example.satchelbooksharing.viewModel.satchel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.loadAppState()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        //Text("STATE: $state")

        when {
            state == null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Cargando...")
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }
            }

            state?.isUserLoggedIn == true -> {
                LaunchedEffect("goHome") {
                    navController.navigate(ScreensRoute.ScreenHomeRoute.route) {
                        popUpTo(ScreensRoute.ScreenSplashRoute.route) { inclusive = true }
                    }
                }
            }

            else -> {
                LaunchedEffect("goAuth") {
                    navController.navigate(ScreensRoute.ScreenAuthRoute.route) {
                        popUpTo(ScreensRoute.ScreenSplashRoute.route) { inclusive = true }
                    }
                }
            }
        }
    }
}
