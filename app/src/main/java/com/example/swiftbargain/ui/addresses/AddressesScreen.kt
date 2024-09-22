package com.example.swiftbargain.ui.addresses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.addresses.composable.AddressesAddAddress
import com.example.swiftbargain.ui.addresses.view_model.AddressesEvents
import com.example.swiftbargain.ui.addresses.view_model.AddressesInteractions
import com.example.swiftbargain.ui.addresses.view_model.AddressesUiState
import com.example.swiftbargain.ui.addresses.view_model.AddressesViewModel
import com.example.swiftbargain.ui.composable.AddressItem
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.composable.PrimaryDeleteItemDialog
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler
import com.example.swiftbargain.ui.utils.SnackBarManager
import com.example.swiftbargain.ui.utils.SnackBarManager.showSuccess
import com.example.swiftbargain.ui.utils.UiConstants

@Composable
fun AddressesScreen(
    navController: NavController,
    unAuthorizedLogin: () -> Unit,
    viewModel: AddressesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBar = SnackBarManager.init()
    EventHandler(effects = viewModel.event) { event, scope ->
        when (event) {
            AddressesEvents.AddAddressSuccess -> snackBar.showSuccess(
                UiConstants.ADD_ADDRESS_SUCCESS,
                scope
            )

            AddressesEvents.NavigateToBack -> navController.popBackStack()
            AddressesEvents.UnAuthorizedAccess -> unAuthorizedLogin()
        }
    }
    AddressesContent(state = state, interactions = viewModel)
}

@Composable
private fun AddressesContent(
    state: AddressesUiState,
    interactions: AddressesInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = MaterialTheme.spacing.space16),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
            ) {
                item {
                    PrimaryAppbar(
                        title = stringResource(R.string.address),
                        onClickBack = interactions::onCLickBack
                    )
                }
                items(state.allAddresses) { address ->
                    AddressItem(
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.space16),
                        isSelected = false,
                        address = address,
                        onClickItem = {},
                        onClickDelete = {
                            interactions.onSelectDeleteAddress(address)
                            interactions.controlDeleteAddressDialogVisibility()
                        }
                    )
                }
            }
            AddressesAddAddress(
                isAddAddressVisible = state.isAddAddressVisible,
                addAddress = state.addAddress,
                interactions = interactions
            )
        }

        if (state.isDeleteAddressVisible)
            PrimaryDeleteItemDialog(
                onConfirm = interactions::onDeleteAddress,
                onCancel = interactions::controlDeleteAddressDialogVisibility
            )
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getAllAddresses
    )
}
