package com.example.swiftbargain

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swiftbargain.navigation.Authentication
import com.example.swiftbargain.navigation.AuthenticationNavGraph
import com.example.swiftbargain.navigation.User
import com.example.swiftbargain.navigation.UserNavGraph

@Composable
fun SwiftBargainApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Authentication) {
        composable<Authentication> { AuthenticationNavGraph(mainNavController = navController) }
        composable<User> { UserNavGraph(mainNavController = navController) }
    }
}
