package com.example.satchelbooksharing.data

import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.model.satchel.BookRequest
import kotlinx.coroutines.flow.Flow

interface RequestRepository {
    suspend fun checkIfRequested(bookId: String, userId: String): Boolean
    suspend fun createRequestAndChat(book: Book, requesterId: String)
    suspend fun cancelRequest(book: Book, requesterId: String)
    suspend fun acceptRequest(bookId: String): String?
    suspend fun deleteRequestChat(chatId: String)
    fun getLoansGivenBy(userId: String): Flow<List<BookRequest>>
    fun getLoansReceivedBy(userId: String): Flow<List<BookRequest>>
}