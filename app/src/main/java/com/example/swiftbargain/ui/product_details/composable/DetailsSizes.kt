package com.example.swiftbargain.ui.product_details.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.product_details.view_model.ProductDetailsUiState
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun DetailsSizes(
    modifier: Modifier = Modifier,
    state: ProductDetailsUiState,
    onClickSize: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
    ) {
        Text(
            modifier = Modifier.padding(start = MaterialTheme.spacing.space16),
            text = stringResource(R.string.select_size),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colors.text
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.space16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            items(state.product.sizes) { size ->
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .border(
                                1.dp,
                                if (state.selectedSize == size) MaterialTheme.colors.primary else MaterialTheme.colors.textLight,
                                CircleShape
                            )
                            .clickable { onClickSize(size) }
                    )
                    Text(
                        text = size,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (state.selectedSize == size) MaterialTheme.colors.primary else MaterialTheme.colors.text
                    )
                }
            }
        }
    }
}
