package com.example.swiftbargain.ui.addresses.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.swiftbargain.ui.add_address.AddAddressContent
import com.example.swiftbargain.ui.addresses.view_model.AddressesInteractions
import com.example.swiftbargain.ui.composable.PrimaryFab
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AddressesAddAddress(
    modifier: Modifier = Modifier,
    isAddAddressVisible: Boolean,
    addAddress: AddressUiState,
    interactions: AddressesInteractions
) {
    SharedTransitionLayout {
        AnimatedContent(
            modifier = modifier,
            targetState = isAddAddressVisible,
            label = "transition"
        ) { isVisible ->
            if (isVisible) {
                AddAddressContent(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .sharedBounds(
                            rememberSharedContentState(key = "bounds"),
                            animatedVisibilityScope = this@AnimatedContent,
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                        ),
                    state = addAddress,
                    interactions = interactions,
                    onCancel = interactions::controlAddAddressVisibility
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
                    onClick = interactions::controlAddAddressVisibility
                )
            }
        }
    }
}