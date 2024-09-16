package com.example.swiftbargain.ui.cart_check_out.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutInteractions
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutUiState
import com.example.swiftbargain.ui.composable.ScrollToFirstItemFab
import com.example.swiftbargain.ui.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun ChooseCardContent(
    modifier: Modifier = Modifier,
    state: CartCheckOutUiState,
    interactions: CartCheckOutInteractions
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    ScrollToFirstItemFab(
        modifier = modifier.fillMaxSize(),
        isFabVisible = scrollState.canScrollBackward,
        onClickFab = { scope.launch { scrollState.animateScrollToItem(0) } }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = scrollState,
                contentPadding = PaddingValues(vertical = MaterialTheme.spacing.space16),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
            ) {
                item {
                    AddItemAppbar(
                        title = stringResource(R.string.choose_card),
                        onClickBack = { interactions.onSwitchContent(CartCheckOutUiState.VisibleContent.PAYMENT) },
                        onClickAdd = interactions::controlAddCreditVisibility
                    )
                }
            }
        }
    }
    ChooseCardAddCredit(state = state, interactions = interactions)
}
