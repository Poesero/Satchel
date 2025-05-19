package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.example.satchelbooksharing.ui.satchel.sharedElements.Header
import com.example.satchelbooksharing.ui.satchel.sharedElements.SearchField

@Composable
fun SearchScreen() {

    //Header must have a search bar where the user type the title of the book
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Header(title = "Search bar here" )

        SearchField()

        Column (
            modifier = Modifier
                .weight(1f)
                .padding(24.dp)
        ) {
            Text("Contenido principal en el futuro")
        }

        Footer(navController = rememberNavController(), currentDestination = "search")

    }


}

@Preview (showBackground = true)
@Composable
fun SearchScreenPreview(){
    SearchScreen()
}