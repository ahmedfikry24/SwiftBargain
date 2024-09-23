package com.example.swiftbargain.ui.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors

@Composable
fun PrimaryDeleteItemDialog(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.are_you_sure_that_you_want_delete_this_item_we_can_t_undo_this_action_later),
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        containerColor = MaterialTheme.colors.background,
        title = {
            Text(
                text = stringResource(R.string.alert),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colors.red
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colors.textGrey
            )
        },
        onDismissRequest = onCancel,
        confirmButton = {
            PrimaryTextButton(
                text = stringResource(R.string.ok),
                containerColor = MaterialTheme.colors.red,
                contentColor = MaterialTheme.colors.background,
                onClick = onConfirm
            )
        },
        dismissButton = {
            PrimaryTextButton(
                text = stringResource(R.string.cancel),
                containerColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
                onClick = onCancel
            )
        }
    )
}
