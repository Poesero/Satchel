package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.model.satchel.Genre
import com.example.satchelbooksharing.ui.satchel.navigation.ScreensRoute
import com.example.satchelbooksharing.ui.satchel.sharedElements.AppButton
import com.example.satchelbooksharing.ui.satchel.sharedElements.BookCard
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.example.satchelbooksharing.ui.satchel.sharedElements.Header
import com.example.satchelbooksharing.ui.satchel.sharedElements.SatchelBodyContainer
import com.example.satchelbooksharing.ui.satchel.sharedElements.SearchField

@Composable
fun SearchScreen(navController: NavController, allBooks: List<Book>) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var filteredBooks by remember { mutableStateOf(emptyList<Book>()) }

    fun doSearch() {
        filteredBooks = if (query.text.isNotBlank()) {
            allBooks.filter {
                it.title.contains(query.text, ignoreCase = true)
            }
        } else {
            emptyList()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 5.dp)
        ) {
            SearchField(
                value = query,
                onValueChange = {
                    query = it
                },
                onSearch = {
                    doSearch()
                }
            )
        }

        SatchelBodyContainer(modifier = Modifier.weight(1f)) {
            if (filteredBooks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val msg = if (query.text.isNotBlank()) {
                        "No se encontraron libros que contengan \"${query.text}\"."
                    } else {
                        "Toda aventura comienza con un solo paso."
                    }
                    Text(text = msg)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    contentPadding = PaddingValues(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredBooks) { book ->
                        BookCard(book = book) {
                            navController.navigate("Book/${book.id}")
                        }
                    }
                }
            }
        }

        Footer(navController = navController)
    }
}

@Preview (showBackground = true)
@Composable
fun SearchScreenPreview(){

}