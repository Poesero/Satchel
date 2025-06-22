package com.example.satchelbooksharing.data

import com.example.satchelbooksharing.model.satchel.Book

interface RequestRepository {
    suspend fun checkIfRequested(bookId: String, userId: String): Boolean
    suspend fun createRequestAndChat(book: Book, requesterId: String)
    suspend fun cancelRequest(book: Book, requesterId: String)
    suspend fun acceptRequest(bookId: String)
}