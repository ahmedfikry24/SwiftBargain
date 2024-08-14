package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onClickKeyboardDone: () -> Unit,
    onChangeValue: (String) -> Unit,
    onClickFavourite: () -> Unit,
    onClickNotification: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PrimaryTextField(
            modifier = Modifier.weight(1f),
            value = value,
            hint = stringResource(R.string.search_product),
            isError = false,
            leadingIconId = R.drawable.ic_search,
            imeAction = ImeAction.Search,
            onClickKeyboardDone = onClickKeyboardDone,
            onChangeValue = onChangeValue
        )
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colors.textGrey),
            onClick = onClickFavourite
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_favourite),
                contentDescription = null
            )
        }
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colors.textGrey),
            onClick = onClickNotification
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_notification),
                contentDescription = null
            )
        }
    }
}