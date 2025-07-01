package com.example.satchelbooksharing.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepository {

    suspend fun getOwnerPhotoUrl(ownerId: String): String? {
        return try {
            val doc = Firebase.firestore.collection("profiles")
                .document(ownerId)
                .get()
                .await()

            if (doc.exists()) {
                doc.getString("photoUrl")
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
