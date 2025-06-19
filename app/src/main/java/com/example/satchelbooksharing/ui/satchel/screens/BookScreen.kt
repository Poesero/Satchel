package com.example.satchelbooksharing.ui.satchel.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.satchelbooksharing.R
import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.model.satchel.Genre
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

@Composable
fun BookScreen (navController: NavController, bookId: String){
    var book by remember  { mutableStateOf<Book?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(bookId) {
        try {
            val doc = Firebase.firestore.collection("books").document(bookId).get().await()
            book = doc.toObject(Book::class.java)?.apply { id = doc.id }
        } catch (_: Exception) {
        } finally {
            isLoading = false
        }
    }
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {

        book?.let { b ->
            BookDetailContent(navController = navController, book = b)
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Libro no encontrado.")
            }
        }
    }
}
@Composable
private fun BookDetailContent(navController: NavController, book: Book) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = book.title,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
        )

        val painter = if (!book.imageUri.isNullOrEmpty()) {
            rememberAsyncImagePainter(model = Uri.parse(book.imageUri))
        } else {
            painterResource(id = R.drawable.empty_image)
        }
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(40.dp)
                )
                .border(2.dp, Color.Black, RoundedCornerShape(40.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = book.description,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .align(Alignment.Center)
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.Start

        ) {
            Badge(modifier = Modifier) {
                Text(text = book.genre.displayName)
            }
            Badge(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
            ) {
                Text(text = book.genre.displayName)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.Start

        ) {
            Badge(modifier = Modifier) {
                Text(text = "Pedir")
            }
            Badge(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
            ) {
                Text(text = "Disponible")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(40.dp)
                )
                .border(2.dp, Color.Black, RoundedCornerShape(40.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "6/10",
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )

        }

        Footer(navController)
    }
}

/*@Preview(showBackground = true)
@Composable
fun Prev(){
    val mockBook = Book(
        title = "Mock Title",
        author = "Mock Author",
        description = "This is a mock description. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed scelerisque blandit efficitur. Aenean porttitor ultrices elit et faucibus. Pellentesque imperdiet semper risus vel vehicula. Morbi sit amet sapien id ligula porta condimentum et eget neque. Maecenas eu lorem efficitur, facilisis odio at, sodales eros. Sed auctor vulputate sem, sodales cursus justo aliquet sit amet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Interdum et malesuada fames ac ante ipsum primis in faucibus.",
        genre = Genre.FANTASY, // or whatever enum/class you're using
        imageUri = null
    )

    val navController = rememberNavController()

    //BookScreen(navController = navController, bookId = mockBook)
}
 */

