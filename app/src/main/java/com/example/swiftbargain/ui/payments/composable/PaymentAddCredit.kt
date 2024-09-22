package com.example.swiftbargain.ui.payments.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.swiftbargain.ui.add_credit.AddCreditContent
import com.example.swiftbargain.ui.composable.PrimaryFab
import com.example.swiftbargain.ui.payments.view_model.PaymentsInteractions
import com.example.swiftbargain.ui.payments.view_model.PaymentsUiState
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PaymentAddCredit(
    modifier: Modifier = Modifier,
    state: PaymentsUiState,
    interactions: PaymentsInteractions
) {
    SharedTransitionLayout {
        AnimatedContent(
            modifier = modifier,
            targetState = state.isAddCreditVisible,
            label = "transition"
        ) { isVisible ->
            if (isVisible) {
                AddCreditContent(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .sharedBounds(
                            rememberSharedContentState(key = "bounds"),
                            animatedVisibilityScope = this@AnimatedContent,
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                        ),
                    state = state.addCredit,
                    interactions = interactions,
                    onCancel = interactions::controlAddCreditVisibility
                )
            } else {
                PrimaryFab(
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.space16)
                        .sharedBounds(
                            rememberSharedContentState(key = "bounds"),
                            animatedVisibilityScope = this@AnimatedContent,
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                        ),
                    onClick = interactions::controlAddCreditVisibility
                )
            }
        }
    }
}
