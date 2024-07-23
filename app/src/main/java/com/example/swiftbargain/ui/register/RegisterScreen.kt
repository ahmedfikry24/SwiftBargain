package com.example.swiftbargain.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.ui.register.viiew_model.RegisterInteractions
import com.example.swiftbargain.ui.register.viiew_model.RegisterUiState
import com.example.swiftbargain.ui.register.viiew_model.RegisterViewModel

@Composable
fun RegisterScreen(
    maniNavController: NavController,
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterContent(state = state, interactions = viewModel)

}

@Composable
private fun RegisterContent(
    state: RegisterUiState,
    interactions: RegisterInteractions
) {

}
