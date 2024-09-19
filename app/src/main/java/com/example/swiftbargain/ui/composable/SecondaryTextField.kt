package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun SecondaryTextField(
    modifier: Modifier = Modifier,
    title: String,
    fieldValue: String,
    isValueError: Boolean,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorText: String = stringResource(R.string.this_filed_required),
    onChangeValue: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colors.text
        )
        PrimaryTextField(
            value = fieldValue,
            hint = "",
            isError = isValueError,
            imeAction = imeAction,
            keyboardType = keyboardType,
            errorText = errorText,
            onChangeValue = onChangeValue
        )
    }
}
