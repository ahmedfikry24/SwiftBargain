package com.example.swiftbargain.ui.reviews.composable

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.ControlItemVisibility
import com.example.swiftbargain.ui.composable.NoItemFound
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.composable.RatingStar
import com.example.swiftbargain.ui.composable.ReviewItem
import com.example.swiftbargain.ui.composable.ScrollToFirstItemFab
import com.example.swiftbargain.ui.reviews.view_model.ReviewsInteractions
import com.example.swiftbargain.ui.reviews.view_model.ReviewsUiState
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun ReviewsMainContent(
    modifier: Modifier = Modifier,
    state: ReviewsUiState,
    interactions: ReviewsInteractions
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Column(modifier = modifier.fillMaxSize()) {
        ScrollToFirstItemFab(
            modifier = Modifier.weight(1f),
            isFabVisible = scrollState.canScrollBackward,
            onClickFab = { scope.launch { scrollState.animateScrollToItem(0) } }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = scrollState
            ) {
                item {
                    PrimaryAppbar(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                        title = stringResource(R.string.reviews),
                        onClickBack = interactions::onClickBack
                    )
                }
                item {
                    LazyRow(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.space16),
                        contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.space16),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
                    ) {

                        item {
                            ReviewTab(
                                isSelected = state.selectedFilter == -1,
                                onClick = { interactions.onClickFilterReviews(-1) }
                            ) {
                                Text(
                                    text = stringResource(R.string.all_review),
                                    style = MaterialTheme.typography.headlineSmall,
                                )
                            }
                        }

                        items(5) {
                            ReviewTab(
                                isSelected = state.selectedFilter == it,
                                onClick = { interactions.onClickFilterReviews(it) }
                            ) {
                                RatingStar(
                                    modifier = Modifier.size(MaterialTheme.spacing.space16),
                                    fillFraction = 1f,
                                )
                                Text(
                                    modifier = Modifier.padding(start = MaterialTheme.spacing.space8),
                                    text = it.inc().toString(),
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                        }
                    }
                }
                items(state.filteredReviews) { review ->
                    ControlItemVisibility(isVisible = state.filteredReviews.isNotEmpty()) {
                        ReviewItem(
                            modifier = Modifier
                                .padding(horizontal = MaterialTheme.spacing.space16)
                                .padding(top = MaterialTheme.spacing.space16),
                            state = review
                        )
                    }

                }

                item {
                    NoItemFound(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        isVisible = state.filteredReviews.isEmpty(),
                        isButtonVisible = false,
                        enterTransition = slideInHorizontally(tween(500)) + fadeIn(tween(500)),
                        exitTransition = slideOutHorizontally(tween(500)) + fadeOut(tween(500))
                    )
                }
            }
        }
        PrimaryTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.space16),
            text = stringResource(R.string.write_review),
            onClick = interactions::controlSwitchContent
        )
    }
}

@Composable
private fun ReviewTab(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(MaterialTheme.spacing.space4),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0x1A40BFFF) else MaterialTheme.colors.background,
            contentColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.textGrey
        ),
        contentPadding = PaddingValues(MaterialTheme.spacing.space16),
        onClick = onClick,
        content = content
    )
}