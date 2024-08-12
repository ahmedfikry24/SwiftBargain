package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.CategoryUiSate

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryItem: CategoryUiSate
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(70.dp)
                .border(1.dp, MaterialTheme.colors.textLight, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier.size(24.dp),
                model = categoryItem.url,
                contentDescription = null
            )
        }
        Text(
            text = categoryItem.label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colors.textGrey
        )
    }
}