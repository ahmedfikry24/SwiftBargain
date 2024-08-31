package com.example.swiftbargain.ui.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.ui.favorites.view_model.FavoritesInteractions
import com.example.swiftbargain.ui.favorites.view_model.FavoritesUiState
import com.example.swiftbargain.ui.favorites.view_model.FavoritesViewModel
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->

    }
}

@Composable
private fun FavoritesContent(
    state: FavoritesUiState,
    interactions: FavoritesInteractions
) {

}