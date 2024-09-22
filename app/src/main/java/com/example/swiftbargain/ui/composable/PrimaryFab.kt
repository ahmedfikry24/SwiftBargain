package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun PrimaryFab(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.Add,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        shape = RoundedCornerShape(MaterialTheme.spacing.space8),
        containerColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.background,
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}
