package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.example.satchelbooksharing.ui.satchel.sharedElements.Header

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {

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
            Text("Contenido principal en algun momento")
        }
        
        Footer(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

}