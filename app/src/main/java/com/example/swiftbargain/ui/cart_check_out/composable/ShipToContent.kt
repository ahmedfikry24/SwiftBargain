package com.example.swiftbargain.ui.cart_check_out.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutInteractions
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutUiState
import com.example.swiftbargain.ui.composable.AddressItem
import com.example.swiftbargain.ui.composable.ScrollToFirstItemFab
import com.example.swiftbargain.ui.theme.spacing
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState,
            contentPadding = PaddingValues(vertical = MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            item {
                ShipToAppbar(
                    onClickBack = interactions::onClickBack,
                    onClickAddAddress = interactions::controlAddAddressVisibility
                )
            }
            items(state.allAddresses) { address ->
                AddressItem(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.space16),
                    isSelected = state.selectedAddress == address,
                    address = address,
                    onClickItem = { interactions.onSelectAddress(address) },
                    onClickEdit = interactions::onEditAddress,
                    onClickDelete = interactions::onDeleteAddress
                )
            }
        }
    }
    ShipToAddAddress(
        state = state,
        interactions = interactions
    )
}
