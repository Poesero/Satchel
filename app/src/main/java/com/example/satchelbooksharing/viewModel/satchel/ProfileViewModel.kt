package com.example.satchelbooksharing.viewModel.satchel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.satchelbooksharing.model.satchel.BookRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val _requestsPendientes = mutableStateOf<List<BookRequest>>(emptyList())
    val requestsPendientes: State<List<BookRequest>> = _requestsPendientes

    fun cargarRequestsPendientes() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("requests")
            .whereEqualTo("ownerId", userId)
            .whereEqualTo("status", "pending")
            .get()
            .addOnSuccessListener { result ->
                val lista = result.mapNotNull { doc ->
                    doc.toObject(BookRequest::class.java).copy(requestId = doc.id)
                }
                _requestsPendientes.value = lista
            }
            .addOnFailureListener {
            }
    }


    fun aceptarRequest(request: BookRequest) {
        val requestRef = db.collection("requests").document(request.requestId)
        val libroRef = db.collection("books").document(request.bookId)

        db.runBatch { batch ->
            batch.update(requestRef, "status", "accepted")
            batch.update(libroRef, "disponible", false)
        }.addOnSuccessListener {
            cargarRequestsPendientes()
        }
    }

    fun rechazarRequest(request: BookRequest) {
        db.collection("requests").document(request.requestId)
            .update("status", "rejected")
            .addOnSuccessListener {
                cargarRequestsPendientes()
            }
    }
}
