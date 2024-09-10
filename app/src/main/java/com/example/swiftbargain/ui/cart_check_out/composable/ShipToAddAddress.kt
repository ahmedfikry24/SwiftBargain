package com.example.swiftbargain.ui.cart_check_out.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.ui.add_address.AddAddressContent
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutInteractions
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutUiState
import com.example.swiftbargain.ui.theme.colors

@Composable
fun ShipToAddAddress(
    modifier: Modifier = Modifier,
    state: CartCheckOutUiState,
    interactions: CartCheckOutInteractions,
) {
    val transition = updateTransition(targetState = state.isAddAddressVisible, label = "transition")
    val localConfiguration = LocalConfiguration.current
    val height by transition.animateDp(label = "height") {
        if (it) localConfiguration.screenHeightDp.dp else 0.dp
    }
    val width by transition.animateDp(label = "width") {
        if (it) localConfiguration.screenWidthDp.dp else 0.dp
    }
    val background by transition.animateColor(label = "background") {
        if (it) MaterialTheme.colors.background else Color.Transparent
    }
    transition.AnimatedVisibility(
        modifier = modifier
            .size(width, height)
            .background(background),
        visible = { it },
        enter = slideInHorizontally(tween(500)) + fadeIn(tween(500)),
        exit = slideOutHorizontally(tween(500)) + fadeOut(tween(500))
    ) {
        AddAddressContent(state = state.addAddressUiState, interactions = interactions)
    }
}
