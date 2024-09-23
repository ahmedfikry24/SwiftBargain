package com.example.swiftbargain.ui.cart.composable

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
import com.example.swiftbargain.ui.cart.view_model.CartUiState
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun CartDetails(
    modifier: Modifier = Modifier,
    state: CartUiState
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(MaterialTheme.spacing.space4),
        border = BorderStroke(1.dp, MaterialTheme.colors.textLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16),
        ) {
            ValuesSection(
                title = "items (${state.products.size})",
                value = state.productsPrice.toString()
            )
            if (state.couponDiscount.isNotBlank())
                ValuesSection(
                    title = stringResource(R.string.coupon_discount),
                    value = state.couponDiscount
                )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colors.textLight
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.total_price),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colors.text
                )
                Text(
                    text = "$ ${state.totalPrice}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}


@Composable
private fun ValuesSection(
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
            text = "$ $value",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colors.textGrey
        )
    }
}