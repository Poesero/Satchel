package Model.Satchel

data class BookShell(
    var user : String,
    var collection : ArrayList<Book>
)
{ constructor() :this("",ArrayList())}