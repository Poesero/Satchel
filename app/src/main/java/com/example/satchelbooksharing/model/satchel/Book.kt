package com.example.satchelbooksharing.model.satchel

data class Book (
    var title: String,
    var author: String,
    var description: String,
    var genre: Genre = Genre.OTHER,
    val imageUri: String?
    )

{ constructor() :this("","","",Genre.OTHER,null)}