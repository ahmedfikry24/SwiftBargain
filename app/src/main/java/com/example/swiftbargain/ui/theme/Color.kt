package com.example.swiftbargain.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val primary = Color(0XFF40BFFF)
val background = Color(0XFFFFFFFF)
val text = Color(0XFF223263)
val textGrey = Color(0XFF9098B1)
val textLight = Color(0XFFEBF0FF)
val red = Color(0XFFFB7181)
val yellow = Color(0XFFFFC833)
val green = Color(0XFF53D1B6)
val purple = Color(0XFF5C61F4)

@Immutable
data class ColorPalette(
    val primary: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val text: Color = Color.Unspecified,
    val textGrey: Color = Color.Unspecified,
    val textLight: Color = Color.Unspecified,
    val red: Color = Color.Unspecified,
    val yellow: Color = Color.Unspecified,
    val green: Color = Color.Unspecified,
    val purple: Color = Color.Unspecified,
)

val localColors = staticCompositionLocalOf { ColorPalette() }

val MaterialTheme.colors
    @Composable
    get() = localColors.current