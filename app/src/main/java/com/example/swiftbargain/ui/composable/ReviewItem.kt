package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.ReviewUiState

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    state: ReviewUiState
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colors.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.name.first().uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colors.background
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space4)
                ) {
                    Text(
                        text = state.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colors.text
                    )
                    Text(
                        text = state.email,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colors.textGrey
                    )
                }
                ProductRatingBar(
                    rating = state.rating.toFloat(),
                    starSize = MaterialTheme.spacing.space16
                )
            }
        }

        Text(
            text = state.comment,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colors.textGrey
        )

        Text(
            text = state.date,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colors.textGrey
        )
    }
}
