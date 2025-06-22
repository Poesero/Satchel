package com.example.satchelbooksharing.viewModel.satchel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satchelbooksharing.data.LibraryRepository
import com.example.satchelbooksharing.data.LocalLibraryRepository
import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.model.satchel.Genre
import com.example.satchelbooksharing.model.satchel.UserLibrary
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val repository: LibraryRepository = LocalLibraryRepository()
) : ViewModel() {

    val books: StateFlow<List<Book>> = repository.getBooks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var title by mutableStateOf("")
        private set

    var author by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var genre by mutableStateOf(Genre.OTHER)
        private set

    var currentLibrary by mutableStateOf(UserLibrary())
        private set

    fun onTitleChanged(newTitle : String) { title = newTitle}
    fun onAuthorChanged(newAuthor : String) { author = newAuthor}
    fun onDescriptionChanged(newDesc : String) { description = newDesc}
    fun onGenreChanged(newGenre : Genre) { genre = newGenre}

    fun saveBook(onSuccess: () -> Unit = {}) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val book = Book(
                title = title,
                author = author,
                description = description,
                genre = genre,
                imageUri = null,
                ownerId = currentUser.uid  // Aquí asignamos el dueño
            )
            viewModelScope.launch {
                repository.addBook(book)
                resetFields()
                onSuccess() // Para ejecutar algo después de guardar, opcional
            }
        } else {
            // Aquí podrías manejar caso sin usuario logueado si querés
        }
    }

    private fun resetFields() {
        title = ""
        author = ""
        description = ""
        genre = Genre.OTHER
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

}