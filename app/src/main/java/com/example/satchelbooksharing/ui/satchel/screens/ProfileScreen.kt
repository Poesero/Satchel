package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.example.satchelbooksharing.ui.satchel.sharedElements.Header

@Composable
fun ProfileScreen(navController: NavController) {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Header(navController = navController)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp)
        ) {
            //Contenido principal en algun momento
            Text("Contenido perfil")
        }

        Footer(navController = rememberNavController())
    }

}