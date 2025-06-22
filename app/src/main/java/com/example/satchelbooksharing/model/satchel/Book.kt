package com.example.satchelbooksharing.model.satchel

import com.google.firebase.firestore.Exclude

data class Book(
    var title: String = "",
    var author: String = "",
    var description: String = "",
    var genre: Genre = Genre.OTHER,
    val imageUri: String? = null,
    var isAvailable: Boolean = true,
    var ownerId: String ="",
    val ownerName: String ="",
    @get:Exclude @set:Exclude
    var id: String = ""
)



{ constructor() :this("","","",Genre.OTHER,null)}