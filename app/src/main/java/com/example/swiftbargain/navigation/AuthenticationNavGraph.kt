package com.example.swiftbargain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swiftbargain.ui.login.LoginScreen

@Composable
fun AuthenticationNavGraph(mainNavController: NavController) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {
            LoginScreen(
                mainVavController = mainNavController,
                navController = navController
            )
        }
        composable<Register> { }
    }
}
