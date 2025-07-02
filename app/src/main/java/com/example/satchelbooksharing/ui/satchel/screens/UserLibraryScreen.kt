package com.example.satchelbooksharing.ui.satchel.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.ui.satchel.sharedElements.BookCard
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.example.satchelbooksharing.ui.satchel.sharedElements.Header
import com.example.satchelbooksharing.ui.satchel.sharedElements.SatchelBodyContainer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable
fun UserLibraryScreen(
    navController: NavController,
    userId: String
) {
    var books by remember { mutableStateOf<List<Book>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var userName by remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        try {
            val userDoc = Firebase.firestore.collection("profiles").document(userId).get().await()
            userName = userDoc.getString("name") ?: "Usuario"

            val snapshot = Firebase.firestore.collection("books")
                .whereEqualTo("ownerId", userId)
                .get()
                .await()

            books = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Book::class.java)?.apply { id = doc.id }
            }
        } catch (e: Exception) {
            Log.e("UserLibraryScreen", "Error al cargar libros: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        Header(navController = navController)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp, vertical = 12.dp)
        ) {
            SatchelBodyContainer(modifier = Modifier.weight(1f)) {
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(5.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(books) { book ->
                            BookCard(book = book) {
                                navController.navigate("Book/${book.id}")
                            }
                        }
                    }
                }
            }

        }

        Footer(navController)
    }
}
