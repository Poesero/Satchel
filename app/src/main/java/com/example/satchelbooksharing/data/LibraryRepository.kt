package com.example.satchelbooksharing.data

import com.example.satchelbooksharing.model.satchel.Book
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {
    fun getBooks(): Flow<List<Book>>
    suspend fun addBook(book: Book)
    suspend fun deleteBook(book: Book)
    suspend fun clearLibrary()
}