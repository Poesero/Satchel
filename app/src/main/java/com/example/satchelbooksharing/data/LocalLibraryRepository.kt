package com.example.satchelbooksharing.data

import com.example.satchelbooksharing.model.satchel.Book
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow

class LocalLibraryRepository : LibraryRepository {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    private val books = _books.asStateFlow()

    override fun getBooksByOwner(userId: String): Flow<List<Book>> = callbackFlow {
        val db = Firebase.firestore
        val listener = db.collection("books")
            .whereEqualTo("ownerId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    close(error ?: Exception("Unknown Firestore error"))
                    return@addSnapshotListener
                }

                val books = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Book::class.java)?.copy(id = doc.id)
                }
                trySend(books)
            }

        awaitClose { listener.remove() }
    }


    override fun getAllBooks(): Flow<List<Book>> {
        return books
    }

    override suspend fun addBook(book: Book) {
        val db = Firebase.firestore
        val newDoc = db.collection("books").document()   // crea un ID nuevo
        val bookWithId = book.copy(id = newDoc.id)
        newDoc.set(bookWithId)
    }

    override suspend fun deleteBook(book: Book) {
        _books.value -= book
    }

    override suspend fun clearLibrary() {
        _books.value = emptyList()
    }
}