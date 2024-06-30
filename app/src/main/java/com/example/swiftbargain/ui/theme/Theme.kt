package com.example.swiftbargain.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val colorSystem = ColorPalette(
    primary = primary,
    background = background,
    text = text,
    textGrey = textGrey,
    textLight = textLight,
    red = red,
    yellow = yellow,
    green = green,
    purple = purple,
)

@Composable
fun SwiftBargainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        localColors provides colorSystem
    ) {
        MaterialTheme(
            typography = Typography,
            content = content
        )
    }

}