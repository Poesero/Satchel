package com.example.satchelbooksharing.model.satchel

data class Book (
    var title: String,
    var author: String,
    val imageUri: String?
    )

{ constructor() :this("","","")}