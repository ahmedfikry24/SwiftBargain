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
import com.example.swiftbargain.ui.composable.AddressItem
import com.example.swiftbargain.ui.composable.NoItemFound
import com.example.swiftbargain.ui.composable.PrimaryDeleteItemDialog
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.composable.ScrollToFirstItemFab
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState
import kotlinx.coroutines.launch

@Composable
fun ShipToContent(
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
                        title = stringResource(R.string.ship_to),
                        onClickBack = interactions::onClickBack,
                        onClickAdd = interactions::controlAddAddressVisibility
                    )
                }
                item {
                    NoItemFound(
                        isVisible = state.allAddresses.isEmpty(),
                        buttonText = stringResource(R.string.add_address),
                        onClickAdd = interactions::controlAddAddressVisibility
                    )
                }
                items(state.allAddresses) { address ->
                    AddressItem(
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.space16),
                        isSelected = state.selectedAddress == address,
                        address = address,
                        onClickItem = { interactions.onSelectAddress(address) },
                        onClickDelete = { interactions.onDeleteAddress(address) }
                    )
                }
            }

            PrimaryTextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.space16)
                    .padding(bottom = MaterialTheme.spacing.space16),
                text = stringResource(R.string.next),
                isEnabled = state.selectedAddress != AddressUiState(),
                onClick = { interactions.onSwitchContent(CartCheckOutUiState.VisibleContent.PAYMENT) }
            )
        }
    }
    ShipToAddAddress(
        state = state,
        interactions = interactions
    )
    if (state.isDeleteAddressVisible)
        PrimaryDeleteItemDialog(
            onConfirm = interactions::deleteAddress,
            onCancel = interactions::onDismissDeleteAddressDialog
        )
}
