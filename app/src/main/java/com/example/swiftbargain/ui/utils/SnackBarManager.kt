package com.example.swiftbargain.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.swiftbargain.ui.theme.colors

class SnackBarVisual(
    override val message: String,
    val color: Color,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
) : SnackbarVisuals

object SnackBarManager {
    private var snackBar: SnackbarHostState? = null

    @Composable
    fun init(): SnackbarHostState {
        if (snackBar == null)
            snackBar = remember { SnackbarHostState() }
        return snackBar!!
    }

    @Composable
    fun SnackbarHostState.ShowSuccess(
        message: String,
        actionBefore: () -> Unit = {},
        actionAfter: () -> Unit = {},
    ) {
        val color = MaterialTheme.colors.green
        LaunchedEffect(UInt) {
            actionBefore()
            this@ShowSuccess.showSnackbar(SnackBarVisual(message, color))
            actionAfter()
        }
    }

    @Composable
    fun SnackbarHostState.ShowWarning(
        message: String,
        actionBefore: () -> Unit = {},
        actionAfter: () -> Unit = {},
    ) {
        val color = MaterialTheme.colors.yellow
        LaunchedEffect(UInt) {
            actionBefore()
            this@ShowWarning.showSnackbar(SnackBarVisual(message, color))
            actionAfter()
        }
    }

    @Composable
    fun SnackbarHostState.ShowError(
        message: String,
        actionBefore: () -> Unit = {},
        actionAfter: () -> Unit = {},
    ) {
        val color = MaterialTheme.colors.red
        LaunchedEffect(UInt) {
            actionBefore()
            this@ShowError.showSnackbar(SnackBarVisual(message, color))
            actionAfter()
        }
    }
}


