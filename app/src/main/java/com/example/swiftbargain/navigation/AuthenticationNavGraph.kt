package com.example.swiftbargain.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swiftbargain.ui.composable.PrimarySnackBar
import com.example.swiftbargain.ui.login.LoginScreen
import com.example.swiftbargain.ui.register.RegisterScreen
import com.example.swiftbargain.ui.theme.colors

@Composable
fun AuthenticationNavGraph(mainNavController: NavController) {
    val navController = rememberNavController()
    Scaffold(
        snackbarHost = { PrimarySnackBar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(innerPadding)
        ) {
            NavHost(navController = navController, startDestination = Login) {
                composable<Login> {
                    LoginScreen(
                        mainVavController = mainNavController,
                        navController = navController
                    )
                }
                composable<Register> { RegisterScreen(navController = navController) }
            }
        }
    }
}
