package com.example.swiftbargain.ui.sale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.ui.sale.view_model.SaleInteractions
import com.example.swiftbargain.ui.sale.view_model.SaleUiState
import com.example.swiftbargain.ui.sale.view_model.SaleViewModel
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun SaleScreen(
    navController: NavController,
    viewModel: SaleViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->

    }
    SaleContent(state = state, interactions = viewModel)
}


@Composable
private fun SaleContent(
    state: SaleUiState,
    interactions: SaleInteractions
) {

}