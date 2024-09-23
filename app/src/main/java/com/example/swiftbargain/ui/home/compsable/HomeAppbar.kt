package com.example.swiftbargain.ui.home.compsable

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.vectorResource
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors

@Composable
fun HomeAppbar(
    modifier: Modifier = Modifier,
    onClickFavourite: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colors.textGrey),
            onClick = onClickFavourite
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_favourite_outline),
                contentDescription = null
            )
        }
    }
}
