package com.example.swiftbargain.ui.orders.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.OrderUiState

@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    order: OrderUiState,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.space16),
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(MaterialTheme.spacing.space4),
        border = BorderStroke(1.dp, MaterialTheme.colors.textLight),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
        ) {
            Text(
                text = order.id,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colors.text
            )
            Text(
                text = order.date,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colors.textGrey
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.textGrey
            )
            SectionInfo(
                title = stringResource(R.string.order_status),
                text = order.status.toString()
            )
            SectionInfo(
                title = stringResource(R.string.items),
                text = "${order.numOfItems} Items purchased"
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.price),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colors.textGrey
                )
                Text(
                    text = "$ ${order.price}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }

}

@Composable
private fun SectionInfo(
    modifier: Modifier = Modifier,
    title: String,
    text: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colors.textGrey
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colors.text
        )
    }
}