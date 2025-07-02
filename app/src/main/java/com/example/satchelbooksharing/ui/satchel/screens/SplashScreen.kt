package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.satchelbooksharing.R
import com.example.satchelbooksharing.navigation.ScreensRoute
import com.example.satchelbooksharing.ui.satchel.sharedElements.FadingImageSimple
import com.example.satchelbooksharing.viewModel.satchel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    var isVisible by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.loadAppState()
        isVisible = true
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background_book),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        FadingImageSimple(modifier = Modifier.fillMaxSize(),imageRes = R.drawable.background_color, isVisible = isVisible,contentScale = ContentScale.Crop)

        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.bag_on),
                contentDescription = null,
            )
        }
        when {
            state == null -> {
                    FadingImageSimple(modifier = Modifier.fillMaxWidth(), imageRes = R.drawable.bag_off, isVisible = isVisible)
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
