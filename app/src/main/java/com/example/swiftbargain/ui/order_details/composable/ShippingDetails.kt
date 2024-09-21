package com.example.swiftbargain.ui.order_details.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.swiftbargain.ui.utils.shared_ui_state.ProductUiState

@Composable
fun ShippingDetails(
    modifier: Modifier = Modifier,
    order: OrderUiState,
    products: List<ProductUiState>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.space16)
            .padding(top = MaterialTheme.spacing.space8),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
    ) {
        Text(
            text = stringResource(R.string.shipping_details),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colors.text
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.background,
            shape = RoundedCornerShape(MaterialTheme.spacing.space4),
            border = BorderStroke(1.dp, MaterialTheme.colors.textLight),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.space16),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
            ) {
                SectionInfo(title = stringResource(R.string.date_shipping), value = order.date)
                SectionInfo(
                    title = stringResource(R.string.shipping),
                    value = products.joinToString(",") { it.title }
                )
                val address = order.address
                SectionInfo(
                    title = stringResource(R.string.address),
                    value = "${address.country}/${address.city}/${address.streetAddress} ${address.streetAddress2} \n ${address.phone}"
                )
            }
        }
    }
}


@Composable
private fun SectionInfo(
    modifier: Modifier = Modifier,
    title: String,
    value: String
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
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colors.text
        )
    }
}