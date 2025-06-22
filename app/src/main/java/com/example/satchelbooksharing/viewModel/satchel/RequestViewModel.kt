package com.example.satchelbooksharing.viewModel.satchel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satchelbooksharing.data.FirestoreRequestRepository
import com.example.satchelbooksharing.model.satchel.Book
import kotlinx.coroutines.launch

class RequestViewModel(
    private val repository: FirestoreRequestRepository
) : ViewModel() {

    fun toggleRequest(book: Book, userId: String, requested: Boolean) {
        viewModelScope.launch {
            if (requested) {
                repository.createRequestAndChat(book, userId)
            } else {
                repository.cancelRequest(book, userId)
            }
        }
    }

    suspend fun hasRequested(bookId: String, userId: String): Boolean {
        return repository.checkIfRequested(bookId, userId)
    }

    fun acceptRequest(bookId: String) {
        viewModelScope.launch {
            repository.acceptRequest(bookId)
        }
    }
}
