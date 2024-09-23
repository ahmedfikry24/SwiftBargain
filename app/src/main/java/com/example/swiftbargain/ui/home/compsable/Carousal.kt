package com.example.swiftbargain.ui.home.compsable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.swiftbargain.ui.composable.Banner
import com.example.swiftbargain.ui.composable.CarouselIndicator
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.SaleAdUiState
import kotlinx.coroutines.delay

@Composable
fun Carousal(
    modifier: Modifier = Modifier,
    items: List<SaleAdUiState>,
    onClick: (String, String, String) -> Unit
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
            Banner(
                modifier = Modifier.clickable {
                    onClick(
                        items[page].id,
                        items[page].type,
                        items[page].url
                    )
                },
                url = items[page].url,
                title = items[page].title
            )
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
