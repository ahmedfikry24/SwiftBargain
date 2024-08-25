package com.example.swiftbargain.ui.product_details.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.swiftbargain.ui.composable.Banner
import com.example.swiftbargain.ui.composable.CarouselIndicator
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun DetailsCarousal(
    modifier: Modifier = Modifier,
    imagesUrl: List<String>
) {
    val pagerState = rememberPagerState { imagesUrl.size }
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        HorizontalPager(
            state = pagerState
        ) { page ->
            Banner(url = imagesUrl[page], title = "")
        }
        CarouselIndicator(pagerState = pagerState, listSize = imagesUrl.size)
    }
}
