package com.example.satchelbooksharing.ui.satchel.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// Light theme
private val LightColors = lightColorScheme(
    primary = SatchelYellow,
    onPrimary = Color.Black,
    primaryContainer = SatchelOrange,
    secondary = SatchelDarkOrange,
    onSecondary = Color.White,
    tertiary = SatchelWhite,
    onTertiary = Color.Black,
    background = SatchelCream2,
    onBackground = SatchelBrown,
    surface = Color.White,
    onSurface = SatchelBrown,
    error = SatchelRed,
    onError = Color.White
)

// Dark theme
private val DarkColors = darkColorScheme(
    primary = SatchelYellow,
    onPrimary = Color.Black,
    primaryContainer = SatchelOrange,
    secondary = SatchelDarkOrange,
    onSecondary = Color.Black,
    background = Color(0xFF1C1C1C),
    onBackground = Color(0xFFEDEDED),
    surface = Color(0xFF2B2B2B),
    onSurface = Color(0xFFEDEDED),
    error = SatchelRed,
    onError = Color.White
)

@Composable
fun SatchelTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SatchelTypography,
        shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(12.dp),
            large = RoundedCornerShape(16.dp)
        ),
        content = content
    )
}
