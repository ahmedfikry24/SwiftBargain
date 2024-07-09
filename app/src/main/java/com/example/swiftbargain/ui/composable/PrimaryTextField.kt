package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun PrimaryTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    isError: Boolean,
    errorText: String = stringResource(R.string.this_filed_required),
    leadingIconId: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isSingleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onChangeValue: (String) -> Unit
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colors.textGrey
                )
            },
            leadingIcon = {
                leadingIconId?.also {
                    Icon(
                        imageVector = ImageVector.vectorResource(it),
                        contentDescription = null
                    )
                }
            },
            singleLine = isSingleLine,
            visualTransformation = visualTransformation,
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.textLight,
                errorBorderColor = MaterialTheme.colors.red,
                focusedLeadingIconColor = MaterialTheme.colors.primary,
                unfocusedLeadingIconColor = MaterialTheme.colors.textGrey,
                errorLeadingIconColor = MaterialTheme.colors.red
            ),
            shape = RoundedCornerShape(MaterialTheme.spacing.space4),
            onValueChange = onChangeValue
        )
        if (isError)
            Text(
                modifier = Modifier.padding(top = MaterialTheme.spacing.space8),
                text = errorText,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colors.red
            )
    }
}
