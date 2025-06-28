package com.example.satchelbooksharing.viewModel.satchel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satchelbooksharing.data.FirestoreRequestRepository
import com.example.satchelbooksharing.model.satchel.BookRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val repository = FirestoreRequestRepository()

    private val _requestsPendientes = mutableStateOf<List<BookRequest>>(emptyList())
    val requestsPendientes: State<List<BookRequest>> = _requestsPendientes

    val prestamosDado = mutableStateOf<List<BookRequest>>(emptyList())
    val prestamosRecibido = mutableStateOf<List<BookRequest>>(emptyList())


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
    }

    fun cargarPrestamos() {
        val userId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            repository.getLoansGivenBy(userId).collect {
                prestamosDado.value = it
            }
        }

        viewModelScope.launch {
            repository.getLoansReceivedBy(userId).collect {
                prestamosRecibido.value = it
            }
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
            cargarPrestamos()

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
