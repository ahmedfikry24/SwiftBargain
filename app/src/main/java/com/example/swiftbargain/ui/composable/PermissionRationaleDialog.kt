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
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun PermissionRationaleDialog(
    modifier: Modifier = Modifier,
    message: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        containerColor = MaterialTheme.colors.background,
        title = {
            Text(
                text = stringResource(R.string.permission_required),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colors.text
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colors.textGrey
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            PermissionButton(
                text = stringResource(R.string.ok),
                onClick = onConfirm
            )
        },
        dismissButton = {
            PermissionButton(
                text = stringResource(R.string.cancel),
                containerColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
                onClick = onCancel
            )
        }
    )

}

@Composable
private fun PermissionButton(
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.background,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(MaterialTheme.spacing.space4),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}