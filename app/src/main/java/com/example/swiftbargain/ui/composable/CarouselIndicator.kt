package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing


@Composable
fun CarouselIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    listSize: Int
) {
    Row(modifier = modifier) {
        repeat(listSize) { index ->
            val color =
                if (pagerState.currentPage == index) MaterialTheme.colors.primary else MaterialTheme.colors.textLight
            Box(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.space8)
                    .background(color, CircleShape)
                    .size(MaterialTheme.spacing.space8)
            )
        }
    }
}
