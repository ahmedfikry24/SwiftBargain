package com.example.swiftbargain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swiftbargain.navigation.Authentication
import com.example.swiftbargain.navigation.AuthenticationNavGraph
import com.example.swiftbargain.navigation.User
import com.example.swiftbargain.navigation.UserNavGraph
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility

@Composable
fun SwiftBargainApp(viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ContentLoading(isVisible = state.isLoading)
    ContentVisibility(isVisible = !state.isLoading) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = if (state.isLogin) User else Authentication
        ) {
            composable<Authentication> { AuthenticationNavGraph(mainNavController = navController) }
            composable<User> { UserNavGraph(mainNavController = navController) }
        }
    }
}
