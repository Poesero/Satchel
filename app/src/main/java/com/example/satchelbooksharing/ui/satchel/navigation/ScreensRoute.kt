package com.example.satchelbooksharing.ui.satchel.navigation

sealed class ScreensRoute(val route: String){
    object ScreenSplashRoute : ScreensRoute(route = "Splash")
    object ScreenHomeRoute : ScreensRoute(route = "Home")
    object ScreenLibraryRoute : ScreensRoute(route = "Library")
    object ScreenSearchRoute : ScreensRoute(route = "Search")
    object ScreenProfileRoute : ScreensRoute(route = "Profile")
    object ScreenBookRoute : ScreensRoute(route = "Book")
    object ScreenNewBookRoute : ScreensRoute(route = "NewBook")
    object ScreenChatRoute : ScreensRoute(route = "Chat")
    object AuthRoute : ScreensRoute(route = "Auth")
    object AppRoute : ScreensRoute(route = "App")
}