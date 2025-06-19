package com.example.satchelbooksharing.ui.satchel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.satchelbooksharing.data.FirestoreLibraryRepository
import com.example.satchelbooksharing.ui.satchel.navigation.SatchelNav
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelTheme
import com.example.satchelbooksharing.viewModel.satchel.AuthViewModel
import com.example.satchelbooksharing.viewModel.satchel.LibraryViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        val repo = FirestoreLibraryRepository()
        val libraryViewModel = LibraryViewModel(repo)
        val authViewModel = AuthViewModel()

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
