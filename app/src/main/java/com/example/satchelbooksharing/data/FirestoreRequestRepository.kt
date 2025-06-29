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

        val requestDoc = requestCollection.document()
        val request = BookRequest(
            id = requestDoc.id,
            bookId = book.id,
            bookTitle = book.title,
            requesterId = requesterId,
            requesterName = requesterName,
            ownerId = book.ownerId,
            ownerName = ownerName,
            chatId = chatId
        )

        requestDoc.set(request).await()
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

    override suspend fun acceptRequest(bookId: String): String? {
        val requestSnapshot = requestCollection
            .whereEqualTo("bookId", bookId)
            .whereEqualTo("status", "pending")
            .get()
            .await()

        if (requestSnapshot.isEmpty) return null

        val requestDoc = requestSnapshot.documents.first()
        val requestRef = requestDoc.reference
        val requestData = requestDoc.data ?: return null

        val requesterId = requestData["requesterId"] as? String ?: return null
        val ownerId = requestData["ownerId"] as? String ?: return null
        val chatId = "${bookId}_$requesterId"

        val bookRef = booksCollection.document(bookId)
        val chatRef = Firebase.firestore.collection("chats").document(chatId)

        Firebase.firestore.runBatch { batch ->
            batch.update(requestRef, mapOf(
                "accepted" to true,
                "status" to "accepted",
                "timestamp" to System.currentTimeMillis()
            ))
            batch.update(bookRef, "available", false)
            batch.set(chatRef, mapOf(
                "chatId" to chatId,
                "bookId" to bookId,
                "ownerId" to ownerId,
                "requesterId" to requesterId,
                "active" to true,
                "timestamp" to System.currentTimeMillis()
            ))
        }.await()

        return requesterId
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

    suspend fun getActiveRequest(bookId: String, userId: String): BookRequest? {
        val snapshot = Firebase.firestore.collection("requests")
            .whereEqualTo("bookId", bookId)
            .get()
            .await()

        return snapshot.documents
            .mapNotNull { it.toObject(BookRequest::class.java)?.apply { id = it.id } }
            .firstOrNull {
                (it.requesterId == userId || it.ownerId == userId) && it.status != "pending"
            }
    }



}
