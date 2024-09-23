package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
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
    imeAction: ImeAction = ImeAction.Done,
    isSingleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onClickKeyboardDone: () -> Unit = {},
    onChangeValue: (String) -> Unit
) {
    Column(modifier = modifier) {
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = value,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onClickKeyboardDone()
                },
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
                onSearch = {
                    focusManager.clearFocus()
                    onClickKeyboardDone()
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
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
