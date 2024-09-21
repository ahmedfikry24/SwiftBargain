package com.example.swiftbargain.ui.order_details.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.ProductUiState

@Composable
fun OrderProducts(
    modifier: Modifier = Modifier,
    products: List<ProductUiState>,
    onClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.space16)
            .padding(top = MaterialTheme.spacing.space8),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8)
    ) {
        Text(
            modifier = Modifier.padding(bottom = MaterialTheme.spacing.space4),
            text = stringResource(R.string.products),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colors.text
        )
        products.forEach { product ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(MaterialTheme.spacing.space4),
                border = BorderStroke(1.dp, MaterialTheme.colors.textLight),
                onClick = { onClick(product.id) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.space16),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8)
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.size(72.dp),
                        model = product.url.first(),
                        contentDescription = null,
                    ) {
                        if (painter.state is AsyncImagePainter.State.Loading)
                            CircularProgressIndicator(
                                strokeWidth = 1.dp,
                                color = MaterialTheme.colors.primary
                            )
                        else SubcomposeAsyncImageContent()
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = product.title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colors.text
                        )

                        Text(
                            text = "$ ${product.price}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colors.primary
                        )

                    }
                }
            }
        }
    }
}
