package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun PrimaryDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onCancel: (() -> Unit)? = null,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        containerColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(MaterialTheme.spacing.space8),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colors.text
            )
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
                color = Color.Black
            )
        },
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.background
                ),
                shape = RoundedCornerShape(MaterialTheme.spacing.space4),
                onClick = onConfirm
            ) {
                Text(
                    text = stringResource(R.string.ok),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                )
            }
        },
        dismissButton = {
            if (onCancel != null)
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.text
                    ),
                    shape = RoundedCornerShape(MaterialTheme.spacing.space4),
                    onClick = onCancel
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                    )
                }
        }
    )
}
