package com.example.satchelbooksharing.data

import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.model.satchel.BookRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreRequestRepository : RequestRepository {

    private val db = Firebase.firestore

    override suspend fun createRequestAndChat(book: Book, requesterId: String) {
        val ownerId = book.ownerId ?: return
        val request = BookRequest(
            bookId = book.id,
            requesterId = requesterId,
            ownerId = ownerId
        )

        db.collection("requests")
            .document("${book.id}_$requesterId")
            .set(request)
            .await()

        val chatId = "${book.id}_$requesterId"
        val chatData = mapOf(
            "bookId" to book.id,
            "participants" to listOf(ownerId, requesterId),
            "createdAt" to System.currentTimeMillis()
        )

        db.collection("chats")
            .document(chatId)
            .set(chatData)
            .await()
    }

    override suspend fun cancelRequest(book: Book, requesterId: String) {
        db.collection("requests")
            .document("${book.id}_$requesterId")
            .delete()
            .await()
    }

    override suspend fun checkIfRequested(bookId: String, requesterId: String): Boolean {
        val doc = db.collection("requests")
            .document("${bookId}_$requesterId")
            .get()
            .await()
        return doc.exists()
    }

    override suspend fun acceptRequest(bookId: String) {
        try {
            db.collection("books")
                .document(bookId)
                .update("isAvailable", false)
                .await()
        } catch (e: Exception) {
            throw e
        }
    }
}