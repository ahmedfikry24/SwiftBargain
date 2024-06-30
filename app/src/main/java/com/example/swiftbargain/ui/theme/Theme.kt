package com.example.swiftbargain.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun SwiftBargainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        localColors provides MaterialTheme.colors,
        localSpacing provides MaterialTheme.spacing
    ) {
        MaterialTheme(
            typography = Typography,
            content = content
        )
    }

}