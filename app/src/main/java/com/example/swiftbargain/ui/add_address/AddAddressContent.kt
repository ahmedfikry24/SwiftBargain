package com.example.swiftbargain.ui.add_address

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.composable.SecondaryTextField
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState
import com.example.swiftbargain.ui.utils.shred_interactions.AddAddressInteractions

@Composable
fun AddAddressContent(
    modifier: Modifier = Modifier,
    state: AddressUiState,
    interactions: AddAddressInteractions,
    onCancel: () -> Unit
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space24)
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    PrimaryTextButton(
                        modifier = Modifier.align(Alignment.End),
                        text = stringResource(R.string.cancel),
                        containerColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.textGrey,
                        border = BorderStroke(1.dp, MaterialTheme.colors.textLight),
                        onClick = onCancel
                    )
                }
            }
            item {
                SecondaryTextField(
                    title = stringResource(R.string.country),
                    fieldValue = state.country,
                    isValueError = state.countryError,
                    onChangeValue = interactions::onChangeCountry
                )
            }
            item {
                SecondaryTextField(
                    title = stringResource(R.string.full_name),
                    fieldValue = state.name,
                    isValueError = state.nameError,
                    onChangeValue = interactions::onChangeName
                )
            }

            item {
                SecondaryTextField(
                    title = stringResource(R.string.street_address),
                    fieldValue = state.streetAddress,
                    isValueError = state.streetAddressError,
                    onChangeValue = interactions::onChangeStreetAddress
                )
            }

            item {
                SecondaryTextField(
                    title = stringResource(R.string.street_address) + stringResource(R.string.optional),
                    fieldValue = state.streetAddress2,
                    isValueError = false,
                    onChangeValue = interactions::onChangeStreetAddress2
                )
            }

            item {
                SecondaryTextField(
                    title = stringResource(R.string.city),
                    fieldValue = state.city,
                    isValueError = state.cityError,
                    onChangeValue = interactions::onChangeCity
                )
            }

            item {
                SecondaryTextField(
                    title = stringResource(R.string.zip_code),
                    fieldValue = state.zipCode,
                    isValueError = state.zipCodeError,
                    keyboardType = KeyboardType.Number,
                    onChangeValue = interactions::onChangeZipCode
                )
            }

            item {
                SecondaryTextField(
                    title = stringResource(R.string.phone_number),
                    fieldValue = state.phone,
                    isValueError = state.phoneError,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                    onChangeValue = interactions::onChangePhone
                )
            }

            item {
                PrimaryTextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.space8),
                    text = stringResource(R.string.add_address),
                    onClick = interactions::onClickAddAddress
                )
            }
        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::onClickAddAddress
    )
}