package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.satchelbooksharing.R
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.example.satchelbooksharing.ui.satchel.sharedElements.Header
import com.example.satchelbooksharing.ui.satchel.sharedElements.ImageCarousel
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelCream
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelCream2

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {

    val imageList = listOf(
        R.drawable.promo_1,
        R.drawable.promo_2,
        R.drawable.promo_3,
        R.drawable.promo_4,
        R.drawable.promo_5

    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Header(navController = navController)

        Spacer(modifier = Modifier.height(100.dp))

        ImageCarousel(imageList = imageList)

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