package com.example.satchelbooksharing.data

import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.model.satchel.BookRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.*

class FirestoreRequestRepository : RequestRepository {

    private val db = Firebase.firestore
    private val requestCollection = db.collection("requests")
    private val booksCollection = Firebase.firestore.collection("books")

    override suspend fun checkIfRequested(bookId: String, userId: String): Boolean {
        val snapshot = requestCollection
            .whereEqualTo("bookId", bookId)
            .whereEqualTo("requesterId", userId)
            .get()
            .await()

        return !snapshot.isEmpty
    }

    override suspend fun createRequestAndChat(book: Book, requesterId: String) {
        val chatId = "${book.id}_$requesterId"
        val profilesCollection = Firebase.firestore.collection("profiles")

        val ownerSnapshot = profilesCollection.document(book.ownerId).get().await()
        val requesterSnapshot = profilesCollection.document(requesterId).get().await()

        val ownerName = ownerSnapshot.getString("name") ?: "Dueño"
        val requesterName = requesterSnapshot.getString("name") ?: "Solicitante"

        val request = BookRequest(
            requestId = UUID.randomUUID().toString(),
            bookId = book.id,
            bookTitle = book.title,
            requesterId = requesterId,
            requesterName = requesterName,
            ownerId = book.ownerId,
            ownerName = ownerName,
            chatId = chatId
        )

        requestCollection.document(request.requestId).set(request).await()
    }

    override suspend fun cancelRequest(book: Book, requesterId: String) {
        val snapshot = requestCollection
            .whereEqualTo("bookId", book.id)
            .whereEqualTo("requesterId", requesterId)
            .get()
            .await()

        for (doc in snapshot.documents) {
            doc.reference.delete().await()
        }
    }

    override suspend fun acceptRequest(bookId: String) {
        val requestSnapshot = requestCollection
            .whereEqualTo("bookId", bookId)
            .whereEqualTo("status", "pending")
            .get()
            .await()

        if (requestSnapshot.isEmpty) return

        val requestDoc = requestSnapshot.documents.first()
        val requestRef = requestDoc.reference
        val bookRef = booksCollection.document(bookId)
        Firebase.firestore.runBatch { batch ->
            batch.update(requestRef, mapOf(
                "accepted" to true,
                "status" to "accepted",
                "timestamp" to System.currentTimeMillis()
            ))
            batch.update(bookRef, "disponible", false)
        }.await()
    }


    override fun getLoansGivenBy(userId: String): Flow<List<BookRequest>> = callbackFlow {
        val listener = requestCollection
            .whereEqualTo("ownerId", userId)
            .whereEqualTo("status", "accepted")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    close(error ?: Exception("Unknown error"))
                    return@addSnapshotListener
                }

                val loans = snapshot.documents.mapNotNull { it.toObject(BookRequest::class.java) }
                trySend(loans)
            }

        awaitClose { listener.remove() }
    }

    override fun getLoansReceivedBy(userId: String): Flow<List<BookRequest>> = callbackFlow {
        val listener = requestCollection
            .whereEqualTo("requesterId", userId)
            .whereEqualTo("status", "accepted")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    close(error ?: Exception("Unknown error"))
                    return@addSnapshotListener
                }

                val loans = snapshot.documents.mapNotNull { it.toObject(BookRequest::class.java) }
                trySend(loans)
            }

        awaitClose { listener.remove() }
    }
}
