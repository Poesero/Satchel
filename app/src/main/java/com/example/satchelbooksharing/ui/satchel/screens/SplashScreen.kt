package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.satchelbooksharing.viewModel.satchel.MainViewModel

@Composable
fun SplashScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadAppState()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state == null -> {
                Text("Cargando...")
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
            state?.isUserLoggedIn == true -> {
                LaunchedEffect(Unit) {
                    navController.navigate("home"){
                        popUpTo("splash") { inclusive = true}
                    }
                }
            }
            state?.showContinueButton == true -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Bienvenido a Satchel")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        navController.navigate("home") {
                            popUpTo("splash") {inclusive = true}
                        }
                    }) {
                       Text("Continuar")
                    }
                }
            }
        }
    }
}