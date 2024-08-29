package com.example.swiftbargain.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.swiftbargain.R

@Composable
fun NoItemFound(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    enterTransition: EnterTransition = fadeIn(tween(500)) + scaleIn(tween(500)),
    exitTransition: ExitTransition = fadeOut(tween(500)) + scaleOut(tween(500))
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = enterTransition,
        exit = exitTransition,
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_no_item_found))
        LottieAnimation(
            modifier = Modifier.fillMaxSize(),
            composition = composition
        )
    }
}
