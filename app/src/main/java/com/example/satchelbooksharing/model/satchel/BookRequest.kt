package com.example.satchelbooksharing.model.satchel

data class BookRequest(
    var id: String = "",
    val bookId: String = "",
    val bookTitle: String = "",
    val requesterId: String = "",
    val requesterName: String = "",
    val ownerId: String = "",
    val ownerName: String = "",
    val chatId: String = "",
    val status: String = "pending",
    val delivered: Boolean = false,
    val returned: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
{ }