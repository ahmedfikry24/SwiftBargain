package com.example.swiftbargain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AuthenticationNavGraph(mainNavController: NavController) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {
        composable<Login> { }
        composable<Register> { }
    }
}
