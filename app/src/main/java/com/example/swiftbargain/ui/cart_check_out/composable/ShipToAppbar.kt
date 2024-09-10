package com.example.swiftbargain.ui.cart_check_out.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun ShipToAppbar(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickAddAddress: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 48.dp, maxHeight = 56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(start = MaterialTheme.spacing.space16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colors.textGrey),
                onClick = onClickBack
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_bcak),
                    contentDescription = null
                )
            }
            Text(
                text = stringResource(R.string.ship_to),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colors.text
            )
        }
        IconButton(
            modifier = Modifier.padding(end = MaterialTheme.spacing.space16),
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colors.primary),
            onClick = onClickAddAddress
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_plus),
                contentDescription = null
            )
        }
    }
}