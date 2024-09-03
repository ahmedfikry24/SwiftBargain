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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun PrimaryDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    confirmText: String = stringResource(R.string.ok),
    cancelText: String = stringResource(R.string.cancel),
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
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colors.text
            )
        },
        text = {
            Text(
                text = text,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
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
                    text = confirmText,
                    style = MaterialTheme.typography.titleMedium,
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
                        text = cancelText,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
        }
    )
}
