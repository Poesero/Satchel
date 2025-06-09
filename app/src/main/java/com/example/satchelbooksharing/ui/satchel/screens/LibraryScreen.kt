package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.model.satchel.Genre
import com.example.satchelbooksharing.ui.satchel.sharedElements.BookCard
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.example.satchelbooksharing.ui.satchel.sharedElements.Header
import com.example.satchelbooksharing.ui.satchel.sharedElements.SatchelBodyContainer

@Composable
fun LibraryScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(navController = navController)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp, vertical = 12.dp)
        ) { SatchelBodyContainer {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    item {
                        //FaLta agregar una pantalla dedicada al ABM de los libros
                        //AddBookButton()
                    }
                    items(1) { index ->
                        BookCard(book = Book("Harry Potter", "J. K. Rowling","Buen libro gran libro",Genre.OTHER,null))

                    }

                }

            }

        }

        Footer(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryPreview(){
}