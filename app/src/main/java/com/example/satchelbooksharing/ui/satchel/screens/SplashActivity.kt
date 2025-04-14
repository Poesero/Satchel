package com.example.satchelbooksharing.ui.satchel.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.satchelbooksharing.ui.satchel.MainActivity
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SplashTheme {
               SplashScreen()
            }
        }
    }

    @Composable
    fun SplashScreen() {
        var navigateToMainActivity by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(2345)
            navigateToMainActivity = true
        }

        if (navigateToMainActivity) {
            val context = LocalContext.current
            val intent = Intent(context, MainActivity::class.java)
        }

    }


}