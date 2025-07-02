package com.example.satchelbooksharing.ui.satchel.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.example.satchelbooksharing.R

val HeadlineFontFamily = FontFamily(
    Font(R.font.elegant_typewriter_regular, FontWeight.Normal),
    Font(R.font.elegant_typewriter_bold, FontWeight.Bold)
)

val BodyFontFamily = FontFamily(
    Font(R.font.creatodisplay_regular, FontWeight.Normal),
    Font(R.font.creatodisplay_regularitalic, FontWeight.Bold)
)



val SatchelTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = HeadlineFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp
    ),
    titleLarge = TextStyle(
        fontFamily = HeadlineFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = BodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = BodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        letterSpacing = 0.5.sp
    )
)
