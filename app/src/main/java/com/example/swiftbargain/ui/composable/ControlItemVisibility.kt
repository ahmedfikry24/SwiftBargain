package com.example.swiftbargain.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ControlItemVisibility(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    enterTransition: EnterTransition = slideInHorizontally(tween(500)) + fadeIn(tween(500)),
    exitTransition: ExitTransition = slideOutHorizontally(tween(500)) + fadeOut(tween(500)),
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = enterTransition,
        exit = exitTransition,
        content = content
    )
}
