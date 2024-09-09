package com.example.swiftbargain.ui.cart.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.CartProductUiState

@Composable
fun CartProduct(
    modifier: Modifier = Modifier,
    product: CartProductUiState,
    onClickRemove: (String) -> Unit,
    onChangeQuantity: (Int) -> Unit,
    onClickItem: (String) -> Unit,
) {
    val quantity = product.orderQuantity
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(MaterialTheme.spacing.space4),
        border = BorderStroke(1.dp, MaterialTheme.colors.textLight),
        onClick = { onClickItem(product.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.space16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.space4)),
                model = product.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            ) {
                if (painter.state is AsyncImagePainter.State.Loading)
                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                else SubcomposeAsyncImageContent()
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = MaterialTheme.spacing.space12),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = product.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colors.text,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        modifier = Modifier.padding(start = MaterialTheme.spacing.space8),
                        onClick = { onClickRemove(product.id) },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colors.background,
                            contentColor = MaterialTheme.colors.textGrey
                        )
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_trash),
                            contentDescription = null
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$ ${product.price}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colors.primary
                    )
                    Row {
                        QuantityButton(
                            iconId = R.drawable.ic_minus,
                            onCLick = { if (quantity > 1) onChangeQuantity(quantity - 1) }
                        )
                        Surface(
                            color = MaterialTheme.colors.textLight,
                            shape = RoundedCornerShape(MaterialTheme.spacing.space4)
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    vertical = MaterialTheme.spacing.space4,
                                    horizontal = MaterialTheme.spacing.space8
                                ),
                                text = quantity.toString(),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colors.textGrey
                            )
                        }
                        QuantityButton(
                            iconId = R.drawable.ic_plus,
                            onCLick = { onChangeQuantity(quantity + 1) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun QuantityButton(
    modifier: Modifier = Modifier,
    iconId: Int,
    onCLick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(MaterialTheme.spacing.space4),
        border = BorderStroke(1.dp, MaterialTheme.colors.textLight)
    ) {
        Icon(
            modifier = Modifier
                .padding(MaterialTheme.spacing.space4)
                .clickable { onCLick() },
            imageVector = ImageVector.vectorResource(iconId),
            contentDescription = null,
            tint = MaterialTheme.colors.textGrey
        )
    }
}