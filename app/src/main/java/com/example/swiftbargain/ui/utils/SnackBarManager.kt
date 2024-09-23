package com.example.swiftbargain.ui.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.swiftbargain.ui.theme.green
import com.example.swiftbargain.ui.theme.red
import com.example.swiftbargain.ui.theme.yellow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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


    fun SnackbarHostState.showSuccess(
        message: String,
        scope: CoroutineScope,
        actionBefore: () -> Unit = {},
        actionAfter: () -> Unit = {},
    ) {
        scope.launch {
            actionBefore()
            this@showSuccess.showSnackbar(SnackBarVisual(message, green))
            actionAfter()
        }
    }


    fun SnackbarHostState.showWarning(
        message: String,
        scope: CoroutineScope,
        actionBefore: () -> Unit = {},
        actionAfter: () -> Unit = {},
    ) {
        scope.launch {
            actionBefore()
            this@showWarning.showSnackbar(SnackBarVisual(message, yellow))
            actionAfter()
        }
    }

    fun SnackbarHostState.showError(
        message: String,
        scope: CoroutineScope,
        actionBefore: () -> Unit = {},
        actionAfter: () -> Unit = {},
    ) {
        scope.launch {
            actionBefore()
            this@showError.showSnackbar(SnackBarVisual(message, red))
            actionAfter()
        }
    }
}

