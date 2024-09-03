package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.SnackBarManager
import com.example.swiftbargain.ui.utils.SnackBarVisual

@Composable
fun PrimarySnackBar(modifier: Modifier = Modifier) {
    SnackbarHost(hostState = SnackBarManager.init()) { data ->
        val snackBar = data.visuals as SnackBarVisual
        Snackbar(
            modifier = modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.space12),
            shape = RoundedCornerShape(MaterialTheme.spacing.space4),
            containerColor = snackBar.color
        ) {
            Text(
                text = snackBar.message,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colors.background
            )
        }
    }
}
