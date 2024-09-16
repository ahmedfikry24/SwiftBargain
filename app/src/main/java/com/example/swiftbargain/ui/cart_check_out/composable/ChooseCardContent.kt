package com.example.swiftbargain.ui.cart_check_out.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutInteractions
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutUiState
import com.example.swiftbargain.ui.composable.CreditCardItem
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.composable.ScrollToFirstItemFab
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.CreditUiSate
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

                items(state.allCreditCards) { credit ->
                    val isSelected = state.selectedCreditCard == credit
                    CreditCardItem(
                        card = credit,
                        background = if (isSelected) MaterialTheme.colors.purple else MaterialTheme.colors.primary,
                        onClickItem = { interactions.onSelectCredit(credit) }
                    )
                }
            }
            PrimaryTextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.space16)
                    .padding(bottom = MaterialTheme.spacing.space16),
                text = stringResource(R.string.check_out),
                isEnabled = state.selectedCreditCard != CreditUiSate(),
                onClick = interactions::checkOutOder
            )
        }
    }
    ChooseCardAddCredit(state = state, interactions = interactions)
}
