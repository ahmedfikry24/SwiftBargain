package com.example.swiftbargain.ui.profile.composable

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
fun SaveChangesDialog(
    modifier: Modifier = Modifier,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        containerColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(MaterialTheme.spacing.space8),
        onDismissRequest = onCancel,
        title = {
            Text(
                text = stringResource(R.string.save_changes),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colors.text
            )
        },
        text = {
            Text(
                text = stringResource(R.string.are_you_need_to_save_changes_or_discard),
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
                onClick = onSave
            ) {
                Text(
                    text = stringResource(R.string.save),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        },
        dismissButton = {
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
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    )
}
