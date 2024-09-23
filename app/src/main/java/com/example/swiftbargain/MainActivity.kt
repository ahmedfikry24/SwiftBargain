package com.example.swiftbargain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.swiftbargain.ui.theme.SwiftBargainTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.isActive

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLoginStatus()
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition { !viewModel.scope.isActive }
        setContent {
            SwiftBargainTheme { SwiftBargainApp(viewModel.userUid.isNotBlank()) }
        }
    }
}