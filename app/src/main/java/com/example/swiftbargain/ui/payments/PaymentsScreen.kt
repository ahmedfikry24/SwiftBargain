package com.example.swiftbargain.ui.payments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.CreditCardItem
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.payments.composable.PaymentAddCredit
import com.example.swiftbargain.ui.payments.view_model.PaymentsEvents
import com.example.swiftbargain.ui.payments.view_model.PaymentsInteractions
import com.example.swiftbargain.ui.payments.view_model.PaymentsUiState
import com.example.swiftbargain.ui.payments.view_model.PaymentsViewModel
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler
import com.example.swiftbargain.ui.utils.SnackBarManager
import com.example.swiftbargain.ui.utils.SnackBarManager.showSuccess
import com.example.swiftbargain.ui.utils.UiConstants

@Composable
fun PaymentsScreen(
    navController: NavController,
    viewModel: PaymentsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBar = SnackBarManager.init()
    EventHandler(effects = viewModel.event) { event, scope ->
        when (event) {
            PaymentsEvents.AddCreditSuccess -> snackBar.showSuccess(
                UiConstants.ADD_CREDIT_SUCCESS,
                scope
            )

            PaymentsEvents.NavigateToBack -> navController.popBackStack()
        }
    }
    PaymentsContent(state = state, interactions = viewModel)
}

@Composable
private fun PaymentsContent(
    state: PaymentsUiState,
    interactions: PaymentsInteractions
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
                        title = stringResource(R.string.payment),
                        onClickBack = interactions::onClickBack
                    )
                }
                items(state.allCredits) { credit ->
                    CreditCardItem(
                        card = credit,
                        onClickItem = {}
                    )
                }
            }
            PaymentAddCredit(state = state, interactions = interactions)
        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getAllCredits
    )
}
