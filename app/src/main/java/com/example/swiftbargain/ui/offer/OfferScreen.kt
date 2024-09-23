package com.example.swiftbargain.ui.offer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.navigation.Sale
import com.example.swiftbargain.ui.composable.Banner
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.SecondaryAppBar
import com.example.swiftbargain.ui.offer.view_model.OfferEvents
import com.example.swiftbargain.ui.offer.view_model.OfferInteractions
import com.example.swiftbargain.ui.offer.view_model.OfferUiState
import com.example.swiftbargain.ui.offer.view_model.OfferViewModel
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun OfferScreen(
    navController: NavController,
    viewModel: OfferViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->
        when (event) {
            is OfferEvents.NavigateToSale -> navController.navigate(
                Sale(
                    event.id,
                    event.title,
                    event.url
                )
            )
        }
    }
    OfferContent(state = state, interactions = viewModel)
}

@Composable
private fun OfferContent(
    state: OfferUiState,
    interactions: OfferInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            item {
                SecondaryAppBar(title = stringResource(R.string.your_offers))
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = MaterialTheme.spacing.space16)
                        .background(
                            MaterialTheme.colors.primary,
                            RoundedCornerShape(MaterialTheme.spacing.space4)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.use_megsl_cupon_for_get_90_off),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colors.background,
                        textAlign = TextAlign.Center
                    )
                }
            }
            items(state.offers) { offer ->
                Banner(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.space16)
                        .clickable {
                            interactions.onClickAd(offer.id, offer.type, offer.url)
                        },
                    url = offer.url,
                    title = offer.title,
                )
            }
        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getSaleAds
    )
}
