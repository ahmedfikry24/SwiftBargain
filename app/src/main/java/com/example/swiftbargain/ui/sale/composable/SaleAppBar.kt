package com.example.swiftbargain.ui.sale.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.PrimaryTextField
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun SaleAppBar(
    modifier: Modifier = Modifier,
    title: String,
    isSearchVisible: Boolean,
    searchText: String,
    isSearchError: Boolean,
    onChangeSearch: (String) -> Unit,
    onClickBack: () -> Unit,
    onClickSearch: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 48.dp, maxHeight = 56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
        ) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colors.textGrey),
                onClick = if (isSearchVisible) onClickSearch else onClickBack
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_bcak),
                    contentDescription = null
                )
            }
            ControlSearchItemVisibility(isVisible = isSearchVisible) {
                PrimaryTextField(
                    modifier = Modifier.weight(1f),
                    value = searchText,
                    hint = stringResource(R.string.find_product),
                    isError = isSearchError,
                    imeAction = ImeAction.Search,
                    onClickKeyboardDone = onClickSearch,
                    onChangeValue = onChangeSearch
                )
            }
            ControlSearchItemVisibility(isVisible = !isSearchVisible) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colors.text
                )
            }
        }
        ControlSearchItemVisibility(isVisible = !isSearchVisible) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colors.textGrey),
                onClick = onClickSearch
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    contentDescription = null
                )
            }
        }
    }
}


@Composable
private fun ControlSearchItemVisibility(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    content: @Composable (AnimatedVisibilityScope.() -> Unit)
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = slideInHorizontally(tween(500)) + fadeIn(tween(500)),
        exit = slideOutHorizontally(tween(500)) + fadeOut(tween(500)),
        content = content
    )
}