package com.example.swiftbargain.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.ui.home.view_model.HomeInteractions
import com.example.swiftbargain.ui.home.view_model.HomeUiState
import com.example.swiftbargain.ui.home.view_model.HomeViewModel
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, scope ->
        when (event) {

            else -> Unit
        }
    }
    HomeContent(state = state, interactions = viewModel)
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    interactions: HomeInteractions
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.spacing.space16)
    ) {

    }
}