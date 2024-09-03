package com.example.swiftbargain.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val red = Color(0XFFFB7181)
val yellow = Color(0XFFFFC833)
val green = Color(0XFF53D1B6)

@Immutable
data class ColorPalette(
    val primary: Color = Color(0XFF40BFFF),
    val background: Color = Color(0XFFFFFFFF),
    val text: Color = Color(0XFF223263),
    val textGrey: Color = Color(0XFF9098B1),
    val textLight: Color = Color(0XFFEBF0FF),
    val red: Color = Color(0XFFFB7181),
    val yellow: Color = Color(0XFFFFC833),
    val green: Color = Color(0XFF53D1B6),
    val purple: Color = Color(0XFF5C61F4)
)

val localColors = staticCompositionLocalOf { ColorPalette() }

val MaterialTheme.colors
    @Composable
    get() = localColors.current