package com.example.swiftbargain.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun LoginScreen(
    mainVavController: NavController,
    navController: NavController,

    ) {
    LoginContent()
}

@Composable
private fun LoginContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space8)
    ) {

    }
}
