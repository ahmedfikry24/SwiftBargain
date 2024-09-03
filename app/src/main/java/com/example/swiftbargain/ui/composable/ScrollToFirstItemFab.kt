package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun ScrollToFirstItemFab(
    modifier: Modifier = Modifier,
    isFabVisible: Boolean,
    onClickFab: () -> Unit,
    underContent: @Composable () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        underContent()
        ControlItemVisibility(
            modifier = Modifier,
            isVisible = isFabVisible
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(MaterialTheme.spacing.space16),
                shape = CircleShape,
                containerColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.textGrey,
                onClick = onClickFab
            ) {
                Icon(
                    modifier = Modifier.rotate(90f),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_bcak),
                    contentDescription = null
                )
            }
        }
    }
}
