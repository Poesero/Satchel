package com.example.satchelbooksharing.data

import com.example.satchelbooksharing.model.satchel.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocalLibraryRepository : LibraryRepository {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    private val books = _books.asStateFlow()

    override fun getBooks(): Flow<List<Book>> {
        return books
    }

    override suspend fun addBook(book: Book) {
        _books.value = _books.value + book
    }

    override suspend fun deleteBook(book: Book) {
        _books.value = _books.value - book
    }

    override suspend fun clearLibrary() {
        _books.value = emptyList()
    }
}