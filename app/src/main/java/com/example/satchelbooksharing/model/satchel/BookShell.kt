package Model.Satchel

import com.example.satchelbooksharing.model.satchel.Book

data class BookShell(
    var user : String,
    var collection : ArrayList<Book>
)
{ constructor() :this("",ArrayList())}