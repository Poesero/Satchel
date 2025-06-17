package com.example.satchelbooksharing.ui.satchel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.satchelbooksharing.ui.satchel.navigation.SatchelNav
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelTheme
import com.example.satchelbooksharing.viewModel.satchel.AuthViewModel
import com.example.satchelbooksharing.viewModel.satchel.LibraryViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    private val libraryViewModel = LibraryViewModel()
    private val authViewModel = AuthViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            SatchelTheme {
                SatchelNav(
                    libraryViewModel = libraryViewModel,
                    authViewModel = authViewModel
                )
            }
        }
    }
}
