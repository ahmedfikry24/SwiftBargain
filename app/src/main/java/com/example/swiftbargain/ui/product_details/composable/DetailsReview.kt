package com.example.swiftbargain.ui.product_details.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.ProductRatingBar
import com.example.swiftbargain.ui.composable.ReviewItem
import com.example.swiftbargain.ui.product_details.view_model.ProductDetailsUiState
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun DetailsReview(
    modifier: Modifier = Modifier,
    state: ProductDetailsUiState,
    onClickMore: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.space16),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.review_product),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colors.text
            )

            Text(
                modifier = Modifier.clickable { onClickMore() },
                text = stringResource(R.string.see_more),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colors.primary
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8)
        ) {
            ProductRatingBar(
                rating = state.product.rate.toFloat(),
                starSize = MaterialTheme.spacing.space16
            )
            Text(
                text = "(${state.reviews.size} Reviews)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colors.textGrey
            )
        }
        ReviewItem(
            modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
            state = state.reviews.first()
        )
    }
}
