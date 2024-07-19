package com.example.swiftbargain.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun ContentError(isVisible: Boolean, onTryAgain: () -> Unit) {
    val size = (LocalConfiguration.current.screenWidthDp / 2).dp
    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = isVisible,
        enter = fadeIn(tween(500)) + scaleIn(tween(500)),
        exit = fadeOut(tween(500)) + scaleOut(tween(500)),
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_error))
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8)
        ) {
            LottieAnimation(
                modifier = Modifier.size(size),
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
            Text(
                text = stringResource(R.string.something_went_wrong_check_internet_connection_then_try_again),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colors.textGrey
            )
            TextButton(
                modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                onClick = onTryAgain,
                contentPadding = PaddingValues(MaterialTheme.spacing.space8),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.background
                ),
                shape = RoundedCornerShape(MaterialTheme.spacing.space4)
            ) {
                Text(
                    text = stringResource(R.string.try_again),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}