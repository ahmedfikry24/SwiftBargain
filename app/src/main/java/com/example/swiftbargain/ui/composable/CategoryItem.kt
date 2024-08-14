package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.CategoryUiSate

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryItem: CategoryUiSate,
    onCLick: (String) -> Unit
) {
    Column(
        modifier = modifier.clickable { onCLick(categoryItem.id) },
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(70.dp)
                .border(1.dp, MaterialTheme.colors.textLight, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.size(24.dp),
                model = categoryItem.url,
                contentDescription = null,
            ) {
                if (painter.state is AsyncImagePainter.State.Loading)
                    CircularProgressIndicator(
                        strokeWidth = 1.dp,
                        color = MaterialTheme.colors.primary
                    )
                else SubcomposeAsyncImageContent()
            }
        }
        Text(
            text = categoryItem.label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colors.textGrey
        )
    }
}