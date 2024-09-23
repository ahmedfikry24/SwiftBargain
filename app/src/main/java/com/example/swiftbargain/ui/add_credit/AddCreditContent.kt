package com.example.swiftbargain.ui.add_credit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.example.swiftbargain.ui.utils.shared_ui_state.CreditUiSate
import com.example.swiftbargain.ui.utils.shred_interactions.AddCreditInteractions

@Composable
fun AddCreditContent(
    modifier: Modifier = Modifier,
    state: CreditUiSate,
    interactions: AddCreditInteractions,
    onCancel: () -> Unit
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        Column(modifier = modifier.fillMaxSize()) {
            LazyColumn(
                modifier = modifier.weight(1f),
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
                        title = stringResource(R.string.card_number),
                        fieldValue = state.cardNum,
                        errorText = stringResource(R.string.incorrect_number),
                        isValueError = state.cardNumError,
                        onChangeValue = interactions::onChangeCreditNumber
                    )
                }
                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
                    ) {
                        SecondaryTextField(
                            modifier = Modifier.weight(1f),
                            title = stringResource(R.string.expiration_date),
                            fieldValue = state.expiration,
                            isValueError = state.expirationError,
                            onChangeValue = interactions::onChangeExpiration
                        )
                        SecondaryTextField(
                            modifier = Modifier.weight(1f),
                            title = stringResource(R.string.security_code),
                            fieldValue = state.securityCode,
                            isValueError = state.securityCodeError,
                            onChangeValue = interactions::onChangeSecurityNumber
                        )
                    }
                }

                item {
                    SecondaryTextField(
                        title = stringResource(R.string.card_holder),
                        fieldValue = state.holderName,
                        isValueError = state.holderNameError,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        onChangeValue = interactions::onChangeHolderNMame
                    )
                }
            }

            PrimaryTextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.space16)
                    .padding(bottom = MaterialTheme.spacing.space16),
                text = stringResource(R.string.add_card),
                onClick = interactions::addCard
            )
        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::addCard
    )
}