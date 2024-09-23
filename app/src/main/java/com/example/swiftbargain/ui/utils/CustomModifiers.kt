package com.example.swiftbargain.ui.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.controlVerticalGridPadding(
    isStart: Boolean,
    space: Dp = 16.dp
): Modifier {
    return if (isStart) this.padding(start = space) else this.padding(end = space)
}
