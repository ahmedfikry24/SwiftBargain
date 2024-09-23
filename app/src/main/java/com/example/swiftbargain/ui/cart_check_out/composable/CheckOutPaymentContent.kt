package com.example.swiftbargain.ui.cart_check_out.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutInteractions
import com.example.swiftbargain.ui.cart_check_out.view_model.CartCheckOutUiState
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.PaymentMethod

@Composable
fun CheckOutPaymentContent(
    modifier: Modifier = Modifier,
    payment: PaymentMethod,
    interactions: CartCheckOutInteractions
) {
    val buttonValues = when (payment) {
        PaymentMethod.DEFAULT -> Pair(stringResource(R.string.check_out)) {}
        PaymentMethod.CASH_ON_DELIVERY -> Pair(stringResource(R.string.check_out)) { interactions.checkOutOder() }
        PaymentMethod.CREDIT_CARD -> Pair(stringResource(R.string.next)) {
            interactions.onSwitchContent(
                CartCheckOutUiState.VisibleContent.CHOOSE_CARD
            )
        }

        PaymentMethod.PAYPAL -> Pair(stringResource(R.string.next)) {
            interactions.onSwitchContent(
                CartCheckOutUiState.VisibleContent.CHOOSE_CARD
            )
        }

        PaymentMethod.BANK_TRANSFER -> Pair(stringResource(R.string.next)) {
            interactions.onSwitchContent(
                CartCheckOutUiState.VisibleContent.CHOOSE_CARD
            )
        }
    }
    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .weight(1f)
                .padding(vertical = MaterialTheme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            PrimaryAppbar(
                title = stringResource(R.string.payment),
                onClickBack = { interactions.onSwitchContent(CartCheckOutUiState.VisibleContent.SHIP_TO) }
            )
            ItemSection(
                iconId = R.drawable.ic_cash_on_delivery,
                text = stringResource(R.string.cash_on_delivery),
                isSelected = payment == PaymentMethod.CASH_ON_DELIVERY,
                onClick = { interactions.onChoosePaymentMethod(PaymentMethod.CASH_ON_DELIVERY) }
            )
            ItemSection(
                iconId = R.drawable.ic_credit_card,
                text = stringResource(R.string.credit_card),
                isSelected = payment == PaymentMethod.CREDIT_CARD,
                onClick = { interactions.onChoosePaymentMethod(PaymentMethod.CREDIT_CARD) }
            )
            ItemSection(
                iconId = R.drawable.ic_paypal,
                text = stringResource(R.string.paypal),
                isSelected = payment == PaymentMethod.PAYPAL,
                onClick = { interactions.onChoosePaymentMethod(PaymentMethod.PAYPAL) }
            )
            ItemSection(
                iconId = R.drawable.ic_bank,
                text = stringResource(R.string.bank_transfer),
                isSelected = payment == PaymentMethod.BANK_TRANSFER,
                onClick = { interactions.onChoosePaymentMethod(PaymentMethod.BANK_TRANSFER) }
            )
        }
        PrimaryTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.space16)
                .padding(bottom = MaterialTheme.spacing.space16),
            text = buttonValues.first,
            isEnabled = payment != PaymentMethod.DEFAULT,
            onClick = buttonValues.second
        )
    }
}

@Composable
private fun ItemSection(
    modifier: Modifier = Modifier,
    iconId: Int,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(if (isSelected) MaterialTheme.colors.textLight else MaterialTheme.colors.background)
            .clickable { onClick() }
            .padding(horizontal = MaterialTheme.spacing.space16)

    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.space16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            Image(imageVector = ImageVector.vectorResource(iconId), contentDescription = null)
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colors.text
            )
        }
    }
}