package com.hachitovhummus.androidapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.hachitovhummus.androidapp.R

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

val Assistant = FontFamily(
    Font(R.font.assistant_extralight, FontWeight.ExtraLight),
    Font(R.font.assistant_light, FontWeight.Light),
    Font(R.font.assistant_regular, FontWeight.Normal),
    Font(R.font.assistant_medium, FontWeight.Medium),
    Font(R.font.assistant_semibold, FontWeight.SemiBold),
    Font(R.font.assistant_bold, FontWeight.Bold),
    Font(R.font.assistant_extrabold, FontWeight.ExtraBold)
)

val hummusAppTypography = Typography(
    h1 = TextStyle(
        fontFamily = Assistant,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        letterSpacing = -0.25.sp,
        textDirection = TextDirection.Rtl
    ),
    h2 = TextStyle(
        fontFamily = Assistant,
        fontWeight = FontWeight.SemiBold,
        fontSize = 23.sp,
        letterSpacing = -0.25.sp,
        textDirection = TextDirection.Rtl
    ),
    h3 = TextStyle(
        fontFamily = Assistant,
        fontWeight = FontWeight.Light,
        fontSize = 23.sp,
        letterSpacing = 0.sp,
        textDirection = TextDirection.Rtl
    ),
    h4 = TextStyle(
        fontFamily = Assistant,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 1.sp,
        textDirection = TextDirection.Rtl
    ),
    h5 = TextStyle(
        fontFamily = Assistant,
        fontWeight = FontWeight.Black,
        fontSize = 36.sp,
        letterSpacing = -0.25.sp,
        textDirection = TextDirection.Rtl
    ),
    h6 = TextStyle(
        fontFamily = Assistant,
        fontWeight = FontWeight.Black,
        fontSize = 30.sp,
        letterSpacing = -0.25.sp,
        textDirection = TextDirection.Rtl
    ),
    subtitle1 = TextStyle(
        fontFamily = Assistant,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.75.sp,
        textDirection = TextDirection.Rtl
    )
)

@Composable
fun HachiTovHummusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = hummusAppTypography,
        shapes = Shapes,
        content = content
    )
}