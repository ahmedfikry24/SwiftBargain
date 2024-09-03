package com.example.swiftbargain.ui.login.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.composable.PrimaryTextField
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordBottomSheet(
    value: String,
    isError: Boolean,
    onChangeValue: (String) -> Unit,
    onSend: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.space16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.enter_your_email_address_to_send_a_password_reset_email),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colors.text,
            )
            PrimaryTextField(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.space16),
                value = value,
                hint = stringResource(R.string.example_gmail_com),
                isError = isError,
                leadingIconId = R.drawable.ic_email,
                errorText = stringResource(R.string.email_not_valid),
                keyboardType = KeyboardType.Email,
                onChangeValue = onChangeValue
            )
            PrimaryTextButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.send),
                onClick = onSend
            )
        }
    }
}
