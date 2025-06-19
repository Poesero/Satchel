package com.example.satchelbooksharing.data

import com.example.satchelbooksharing.model.satchel.Book
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreLibraryRepository : LibraryRepository {

    private val db = Firebase.firestore
    private val booksCollection = db.collection("books")

    override fun getBooks(): Flow<List<Book>> = callbackFlow {
        val listener = booksCollection.addSnapshotListener { snapshot, _ ->
            val books = snapshot?.documents?.mapNotNull { doc ->
                val book = doc.toObject(Book::class.java)
                book?.copy(id = doc.id)
            } ?: emptyList()
            trySend(books)
        }
        awaitClose { listener.remove() }
    }

    override suspend fun addBook(book: Book) {
        booksCollection.add(book).await()
    }

    override suspend fun deleteBook(book: Book) {
        val snapshot = booksCollection
            .whereEqualTo("title", book.title)
            .whereEqualTo("author", book.author)
            .get()
            .await()

        for (doc in snapshot.documents) {
            doc.reference.delete().await()
        }
    }

    override suspend fun clearLibrary() {
        val snapshot = booksCollection.get().await()
        for (doc in snapshot.documents) {
            doc.reference.delete().await()
        }
    }
}
