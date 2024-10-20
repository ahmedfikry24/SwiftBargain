package com.example.swiftbargain.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun NoItemFound(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    isButtonVisible: Boolean = true,
    buttonText: String = stringResource(R.string.add),
    enterTransition: EnterTransition = fadeIn(tween(500)) + scaleIn(tween(500)),
    exitTransition: ExitTransition = fadeOut(tween(500)) + scaleOut(tween(500)),
    onClickAdd: () -> Unit = {}
) {
    val rawComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_no_item_found))
    AnimatedVisibility(
        modifier = modifier.fillMaxWidth(),
        visible = isVisible,
        enter = enterTransition,
        exit = exitTransition,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.fillMaxSize(),
                composition = rawComposition
            )
            if (isButtonVisible)
                PrimaryTextButton(
                    text = buttonText,
                    onClick = onClickAdd
                )
        }
    }
}
