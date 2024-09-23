package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.ProductUiState

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    item: ProductUiState,
    isRateVisible: Boolean = true,
    isFavIconVisible: Boolean = false,
    onClickFavIcon: (String) -> Unit = {},
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .border(
                1.dp,
                MaterialTheme.colors.textLight,
                RoundedCornerShape(MaterialTheme.spacing.space4)
            )
            .clickable { onClick(item.id) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.space4)),
                model = item.url.first(),
                contentDescription = null,
                contentScale = ContentScale.Fit
            ) {
                if (painter.state is AsyncImagePainter.State.Loading)
                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                else SubcomposeAsyncImageContent()
            }
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space4)) {
                Text(
                    modifier = Modifier.width(110.dp),
                    text = item.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colors.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (isRateVisible)
                    ProductRatingBar(rating = item.rate.toFloat())
            }

            Text(
                text = "$ ${item.priceAfterDiscount}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colors.primary,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8)
            ) {
                Text(
                    text = "$ ${item.price}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colors.textGrey,
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = "${item.discountPercentage}% off",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colors.red,
                )
                if (isFavIconVisible)
                    IconButton(
                        modifier = Modifier.padding(start = MaterialTheme.spacing.space8),
                        onClick = { onClickFavIcon(item.id) },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colors.background,
                            contentColor = MaterialTheme.colors.red
                        )
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_favorite_fill),
                            contentDescription = null
                        )
                    }
            }
        }
    }
}
