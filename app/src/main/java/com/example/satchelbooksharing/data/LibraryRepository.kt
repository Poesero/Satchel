package com.example.satchelbooksharing.data

import com.example.satchelbooksharing.model.satchel.Book
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {
    fun getAllBooks(): Flow<List<Book>>
    fun getBooksByOwner(userId: String): Flow<List<Book>>
    suspend fun addBook(book: Book)
    suspend fun deleteBook(book: Book)
    suspend fun clearLibrary()
}