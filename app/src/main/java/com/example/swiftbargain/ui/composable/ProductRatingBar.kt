package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.swiftbargain.ui.theme.spacing


@Composable
fun ProductRatingBar(
    modifier: Modifier = Modifier,
    starSize: Dp = MaterialTheme.spacing.space12,
    spaceBetweenStars: Dp = MaterialTheme.spacing.space4,
    rating: Float
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetweenStars),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (index in 1..5) {
            val fillFraction = when {
                rating >= index -> 1.0f
                rating > index - 1 -> rating - (index - 1)
                else -> 0.0f
            }
            RatingStar(
                modifier = Modifier.size(starSize),
                fillFraction = fillFraction
            )
        }
    }
}