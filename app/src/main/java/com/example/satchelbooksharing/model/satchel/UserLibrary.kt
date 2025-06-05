package com.example.satchelbooksharing.model.satchel

data class UserLibrary(
    var user : String,
    var collection : ArrayList<Book> = ArrayList()
)
{ constructor() :this("",ArrayList())}