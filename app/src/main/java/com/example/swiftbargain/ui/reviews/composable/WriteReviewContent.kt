package com.example.swiftbargain.ui.reviews.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.composable.PrimaryTextField
import com.example.swiftbargain.ui.composable.RatingStar
import com.example.swiftbargain.ui.reviews.view_model.ReviewsInteractions
import com.example.swiftbargain.ui.reviews.view_model.ReviewsUiState
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun WriteReviewContent(
    modifier: Modifier = Modifier,
    state: ReviewsUiState,
    interactions: ReviewsInteractions
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            item {
                PrimaryAppbar(
                    title = stringResource(R.string.write_review),
                    onClickBack = interactions::controlSwitchContent
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.space16),
                    text = stringResource(R.string.please_write_overall_level_of_satisfaction_with_your_shipping_delivery_service),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colors.text
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.space16),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (index in 1..5) {
                        RatingStar(
                            modifier = Modifier.size(32.dp),
                            fillFraction = if (state.selectedReviewRate >= index) 1f else 0f,
                            isOnClickOn = true,
                            onClick = { interactions.onSelectReviewRate(index) }
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.space16),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
                ) {
                    Text(
                        text = stringResource(R.string.write_your_review),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colors.text
                    )
                    PrimaryTextField(
                        value = state.reviewComment,
                        hint = stringResource(R.string.write_your_review_here),
                        isSingleLine = false,
                        isError = state.reviewCommentError,
                        onChangeValue = interactions::onChangeReview
                    )
                }
            }
        }
        PrimaryTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.space16),
            text = stringResource(R.string.add_review),
            onClick = interactions::onClickAddReview
        )
    }
}
