package com.example.satchelbooksharing.viewModel.satchel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.model.satchel.UserLibrary
import kotlinx.coroutines.launch

class LibraryViewModel : ViewModel() {

    var currentLibrary by mutableStateOf(UserLibrary())
        private set

    fun loadLibrary(userId: String) {
        viewModelScope.launch {
            currentLibrary = repository.getLibrary(userId)
        }
    }

    fun addBook(book: Book){
        viewModelScope.launch {
            val userId = currentLibrary.user
            repository.addBookToUserLibrary(userId, book)
            currentLibrary.collection.add(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

}