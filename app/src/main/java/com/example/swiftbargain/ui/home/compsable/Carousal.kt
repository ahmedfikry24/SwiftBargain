package com.example.swiftbargain.ui.home.compsable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.SaleAdUiState
import kotlinx.coroutines.delay

@Composable
fun Carousal(
    modifier: Modifier = Modifier,
    items: List<SaleAdUiState>
) {
    val pagerState = rememberPagerState { items.size }
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        HorizontalPager(
            state = pagerState,
            reverseLayout = true
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(MaterialTheme.spacing.space4)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    model = items[page].url,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.space16),
                    text = items[page].title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colors.background
                )
            }
        }
        CarouselIndicator(pagerState = pagerState, listSize = items.size)
    }
    CarouselAutoScroll(pagerState)
}

@Composable
private fun CarouselAutoScroll(
    pagerState: PagerState
) {
    LaunchedEffect(key1 = pagerState.settledPage) {
        delay(3000)
        if (pagerState.canScrollForward)
            pagerState.animateScrollToPage(pagerState.settledPage + 1)
        else
            pagerState.animateScrollToPage(0)
    }
}

@Composable
private fun CarouselIndicator(
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
