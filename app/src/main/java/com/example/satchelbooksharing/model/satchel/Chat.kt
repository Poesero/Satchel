package com.example.satchelbooksharing.model.satchel

data class Chat(
    val chatId: String = "",
    val participantIds: List<String> = listOf(),
    val bookId: String = "",
    val lastMessage: String = "",
    val lastTimestamp: Long = System.currentTimeMillis()
)
{ }