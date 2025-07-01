package com.example.satchelbooksharing.ui.satchel.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.satchelbooksharing.R
import com.example.satchelbooksharing.data.FirestoreRequestRepository
import com.example.satchelbooksharing.data.UserRepository
import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.model.satchel.BookRequest
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.example.satchelbooksharing.ui.satchel.sharedElements.RequestToggleButton
import com.example.satchelbooksharing.viewModel.satchel.RequestViewModel
import com.example.satchelbooksharing.viewModel.satchel.RequestViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun BookScreen(navController: NavController, bookId: String) {

    var book by remember { mutableStateOf<Book?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(bookId) {
        try {
            val doc = FirebaseFirestore.getInstance()
                .collection("books")
                .document(bookId)
                .get()
                .await()
            book = doc.toObject(Book::class.java)?.apply { id = doc.id }
        } catch (e: Exception) {
            // Opcional: log o mensaje
            book = null
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
    val requestRepo = remember { FirestoreRequestRepository() }
    val coroutineScope = rememberCoroutineScope()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val ownerId = book.ownerId
    var activeRequest by remember { mutableStateOf<BookRequest?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val userRepository = remember { UserRepository() }
    var ownerPhotoUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(ownerId) {
        val url = userRepository.getOwnerPhotoUrl(ownerId)
        Log.d("BookScreen", "URL de foto del dueño: $url")
        ownerPhotoUrl = url
    }

    LaunchedEffect(book.id) {
        userId?.let {
            activeRequest = requestRepo.getActiveRequest(book.id, it)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            OwnerInfoPill(
                ownerId = ownerId,
                ownerName = book.ownerName,
                ownerPhotoUrl = ownerPhotoUrl,
                navController = navController
            )
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
                    modifier = Modifier.align(Alignment.CenterVertically)
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
                    .background(Color.LightGray, RoundedCornerShape(40.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(40.dp))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = book.description,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Badge { Text(book.genre.displayName) }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (userId != null && ownerId != null && userId == ownerId) {
                    Button(
                        onClick = { showDeleteDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Borrar")
                    }
                } else if (userId != null && ownerId != null && userId != ownerId && book.available) {
                    RequestToggleButton(
                        requested = activeRequest != null,
                        onToggle = { requested ->
                            coroutineScope.launch {
                                if (requested) {
                                    requestRepo.createRequestAndChat(book, userId!!)
                                    activeRequest = requestRepo.getActiveRequest(book.id, userId)
                                } else {
                                    requestRepo.cancelRequest(book, userId!!)
                                    activeRequest = null
                                }
                            }
                        }
                    )
                }
            }

            Badge {
                Text(text = if (book.available) "Disponible" else "No disponible")
            }
            Spacer(modifier = Modifier.height(16.dp))

            activeRequest?.let { request ->
                when {
                    userId == request.requesterId && request.status == "accepted" && request.delivered != true -> {
                        Button(onClick = {
                            coroutineScope.launch {
                                Firebase.firestore.collection("requests").document(request.id)
                                    .update("delivered", true, "status", "delivered")
                                    .await()
                                activeRequest = request.copy(delivered = true, status = "delivered")
                            }
                        }) {
                            Text("Confirmar recepción")
                        }
                    }
                    userId == request.ownerId && request.delivered == true && request.returned != true -> {
                        Button(onClick = {
                            coroutineScope.launch {
                                Firebase.firestore.collection("requests").document(request.id)
                                    .update("returned", true, "status", "returned")
                                    .await()

                                Firebase.firestore.collection("books").document(book.id)
                                    .update("available", true)
                                    .await()

                                requestRepo.deleteRequestChat(request.chatId)

                                activeRequest = request.copy(returned = true, status = "returned")
                                book.available = true
                            }
                        }) {
                            Text("Confirmar devolución")
                        }
                    }
                }
            }

            LaunchedEffect(book.id, userId) {
                if (userId != null) {
                    activeRequest = requestRepo.getActiveRequest(book.id, userId)
                }
            }
        }

        Footer(navController = navController)
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("¿Confirmar borrado?") },
            text = { Text("¿Estás seguro de que querés borrar este libro? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    coroutineScope.launch {
                        Firebase.firestore.collection("books").document(book.id).delete().await()
                        navController.popBackStack()
                    }
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun OwnerInfoPill(
    ownerId: String,
    ownerName: String,
    ownerPhotoUrl: String?,
    navController: NavController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                navController.navigate("UserLibrary/$ownerId")
            }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            model = ownerPhotoUrl ?: R.drawable.noimagephoto,
            contentDescription = "Foto del dueño",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = ownerName, style = MaterialTheme.typography.bodyMedium)
    }
}
