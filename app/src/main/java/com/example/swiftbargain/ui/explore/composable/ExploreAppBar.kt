package com.example.swiftbargain.ui.explore.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.ControlItemVisibility
import com.example.swiftbargain.ui.composable.PrimaryTextField
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun ExploreAppBar(
    modifier: Modifier = Modifier,
    isSearchVisible: Boolean,
    search: String,
    onClickSearchIcon: () -> Unit,
    onClickMic: () -> Unit,
    onChangeSearch: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.space16),
        horizontalArrangement = Arrangement.End
    ) {
        ControlItemVisibility(
            isVisible = isSearchVisible
        ) {
            Row(modifier = Modifier.weight(1f)) {
                PrimaryTextField(
                    modifier = Modifier.weight(1f),
                    value = search,
                    hint = stringResource(R.string.search_product),
                    isError = false,
                    imeAction = ImeAction.Search,
                    onChangeValue = onChangeSearch
                )
                IconButton(
                    onClick = onClickMic,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.textGrey,
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_mic),
                        contentDescription = null
                    )
                }
            }
        }
        ControlItemVisibility(
            isVisible = !isSearchVisible
        ) {
            IconButton(
                onClick = onClickSearchIcon,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.textGrey
                )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    contentDescription = null
                )
            }
        }
    }
}
