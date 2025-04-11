package Model.Satchel

data class Book (
    var title: String,
    var author: String? = null,
    )

{ constructor() :this("","")}