package com.example.swiftbargain.ui.order_details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.OrderUiState

@Composable
fun OrderStatus(
    modifier: Modifier = Modifier,
    status: OrderUiState.OrderStatus,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.space16),
        Arrangement.spacedBy(MaterialTheme.spacing.space8)
    ) {
        val statusIndex = when (status) {
            OrderUiState.OrderStatus.PACKING -> 0
            OrderUiState.OrderStatus.SHIPPING -> 1
            OrderUiState.OrderStatus.ARRIVING -> 2
            OrderUiState.OrderStatus.SUCCESS -> 3
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.space8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OrderUiState.OrderStatus.entries.forEachIndexed { index, status ->
                val color = if (index <= statusIndex) MaterialTheme.colors.primary
                else MaterialTheme.colors.textGrey
                if (status != OrderUiState.OrderStatus.PACKING) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = color)
                }
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.spacing.space24)
                        .background(color, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        tint = MaterialTheme.colors.background
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderUiState.OrderStatus.entries.forEach { status ->
                Text(
                    text = status.value,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colors.textGrey
                )
            }
        }
    }
}
