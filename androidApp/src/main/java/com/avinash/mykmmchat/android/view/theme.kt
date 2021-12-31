package com.avinash.mykmmchat.android.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avinash.mykmmchat.android.R


val fontFamily = FontFamily(
    Font(R.font.roboto_thin, weight = FontWeight.W100),
    Font(R.font.roboto_regular, weight = FontWeight.W400),
    Font(R.font.roboto_medium, weight = FontWeight.W600),
    Font(R.font.toboto_bold, weight = FontWeight.W700),
)

val appTypography = Typography(
    h1 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp,
        color = Color.Black
    ),
    h2 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        color = Color.Black
    ),
    body1 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W100,
        fontSize = 14.sp,
        color = Color.Black
    ),
    body2 = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W100,
        fontSize = 15.sp,
        color = Color.Black
    ),
)


val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


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


val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)


@Composable
fun MyApplicationTheme(
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
        typography = appTypography,
        shapes = Shapes,
        content = content
    )
}