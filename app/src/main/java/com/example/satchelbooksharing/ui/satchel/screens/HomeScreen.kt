package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.example.satchelbooksharing.ui.satchel.sharedElements.Header

@Composable
fun HomeScreen() {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Header(title = "the Home screen this is")

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp)
        ) {
            //Contenido principal en algun momento
            Text("Contenido principal en algun momento")
        }
        
        Footer(navController = rememberNavController(), currentDestination = "home")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}