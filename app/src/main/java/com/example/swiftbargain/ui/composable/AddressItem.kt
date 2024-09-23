package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    address: AddressUiState,
    onClickItem: () -> Unit,
    onClickDelete: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(MaterialTheme.spacing.space4),
        border = BorderStroke(
            1.dp,
            if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.textLight
        ),
        onClick = onClickItem
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            Text(
                text = address.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colors.text
            )
            Text(
                text = "${address.country}/${address.city}/${address.streetAddress}" + if (address.streetAddress2.isNotEmpty()) ", ${address.streetAddress2}" else "",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colors.textGrey
            )
            Text(
                text = address.phone,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colors.textGrey
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(
                    modifier = Modifier.padding(start = MaterialTheme.spacing.space8),
                    onClick = onClickDelete,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.red
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_trash),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
