package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.model.satchel.Genre
import com.example.satchelbooksharing.ui.satchel.navigation.ScreensRoute
import com.example.satchelbooksharing.ui.satchel.sharedElements.AppButton
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
                .padding(5.dp, vertical = 12.dp)
        ) {
            SatchelBodyContainer {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(5.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    item {
                        Card(modifier = Modifier
                            .width(270.dp)
                            .height(290.dp)
                            .align(Alignment.CenterHorizontally),
                            elevation = CardDefaults.cardElevation(7.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.LightGray
                            )
                        ){
                            Row(modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AppButton(
                                    onClick = {
                                        navController.navigate(ScreensRoute.ScreenNewBookRoute.route)
                                    }) {
                                    Icon(
                                        Icons.Default.AddCircle, contentDescription = null,
                                        modifier = Modifier.size(50.dp)
                                    )
                                }
                            }
                        }
                    }

                    val books = listOf(
                        Book(
                            "Harry Potter",
                            "J. K. Rowling",
                            "Buen libro gran libro, trata de un joven hechicero con una peculiar cicatriz en su frente y un destino lleno de aventuras.",
                            Genre.OTHER,
                            null
                        ),
                        Book(
                            "Harry Potter 2",
                            "J. K. Rowling",
                            "Buen libro gran libro, trata de un joven hechicero con una peculiar cicatriz en su frente y un destino lleno de aventuras.",
                            Genre.OTHER,
                            null
                        ),
                        Book(
                            "Harry Potter 3",
                            "J. K. Rowling",
                            "Buen libro gran libro, trata de un joven hechicero con una peculiar cicatriz en su frente y un destino lleno de aventuras.",
                            Genre.OTHER,
                            null
                        ),
                        Book(
                            "Harry Potter",
                            "J. K. Rowling",
                            "Buen libro gran libro, trata de un joven hechicero con una peculiar cicatriz en su frente y un destino lleno de aventuras.",
                            Genre.OTHER,
                            null
                        ),
                        Book(
                            "Harry Potter 2",
                            "J. K. Rowling",
                            "Buen libro gran libro, trata de un joven hechicero con una peculiar cicatriz en su frente y un destino lleno de aventuras.",
                            Genre.OTHER,
                            null
                        ),
                        Book(
                            "Harry Potter",
                            "J. K. Rowling",
                            "Buen libro gran libro, trata de un joven hechicero con una peculiar cicatriz en su frente y un destino lleno de aventuras.",
                            Genre.OTHER,
                            null
                        ),
                        Book(
                            "Harry Potter 2",
                            "J. K. Rowling",
                            "Buen libro gran libro, trata de un joven hechicero con una peculiar cicatriz en su frente y un destino lleno de aventuras.",
                            Genre.OTHER,
                            null
                        )
                    )

                    items(books) { book ->
                        BookCard(book = book)
                    }
                }
            }
        }
        Footer(navController)
    }
}

