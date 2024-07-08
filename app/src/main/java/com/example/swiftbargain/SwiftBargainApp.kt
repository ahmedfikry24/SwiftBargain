package com.example.swiftbargain

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.swiftbargain.navigation.Authentication
import com.example.swiftbargain.navigation.Home
import com.example.swiftbargain.navigation.Login
import com.example.swiftbargain.navigation.Register
import com.example.swiftbargain.navigation.User

@Composable
fun SwiftBargainApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Authentication) {
        navigation<Authentication>(startDestination = Login) {
            composable<Login> {

            }
            composable<Register> {

            }
        }
        navigation<User>(startDestination = Home) {
            composable<Home> {

            }
        }
    }
}
