package com.example.swiftbargain.ui.product_details.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.ProductRatingBar
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun DetailsTitle(
    modifier: Modifier = Modifier,
    title: String,
    isFavorite: Boolean,
    rate: Float,
    onCLickFavorite: () -> Unit
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
                modifier = Modifier.widthIn(max = (LocalConfiguration.current.screenWidthDp * 2 / 3).dp),
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colors.text
            )
            IconButton(
                colors = IconButtonDefaults.iconButtonColors
                    (
                    contentColor = if (isFavorite) MaterialTheme.colors.red else MaterialTheme.colors.textGrey
                ),
                onClick = onCLickFavorite
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_favourite),
                    contentDescription = null
                )
            }
        }

        ProductRatingBar(
            rating = rate,
            starSize = MaterialTheme.spacing.space16
        )
    }
}
