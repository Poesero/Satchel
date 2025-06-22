package com.example.satchelbooksharing.model.satchel

data class BookRequest(
    val bookId: String = "",
    val bookTitle: String = "",
    val requesterId: String = "",
    val requesterName: String = "",
    val ownerId: String = "",
    val ownerName: String = "",
    val chatId: String = "",
    val accepted: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
 {  }