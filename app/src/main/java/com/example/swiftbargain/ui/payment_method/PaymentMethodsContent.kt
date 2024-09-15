package com.example.swiftbargain.ui.payment_method

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
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.PaymentMethod

@Composable
fun PaymentMethodsContent(
    modifier: Modifier = Modifier,
    payment: PaymentMethod? = null,
    onClickBack: () -> Unit,
    onChooseMethod: (PaymentMethod) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = MaterialTheme.spacing.space16),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
    ) {
        PrimaryAppbar(
            title = stringResource(R.string.payment),
            onClickBack = onClickBack
        )
        ItemSection(
            iconId = R.drawable.ic_cash_on_delivery,
            text = stringResource(R.string.cash_on_delivery),
            isSelected = payment == PaymentMethod.CASH_ON_DELIVERY,
            onClick = { onChooseMethod(PaymentMethod.CASH_ON_DELIVERY) }
        )
        ItemSection(
            iconId = R.drawable.ic_credit_card,
            text = stringResource(R.string.credit_card),
            isSelected = payment == PaymentMethod.CREDIT_CARD,
            onClick = { onChooseMethod(PaymentMethod.CREDIT_CARD) }
        )
        ItemSection(
            iconId = R.drawable.ic_paypal,
            text = stringResource(R.string.paypal),
            isSelected = payment == PaymentMethod.PAYPAL,
            onClick = { onChooseMethod(PaymentMethod.PAYPAL) }
        )
        ItemSection(
            iconId = R.drawable.ic_bank,
            text = stringResource(R.string.bank_transfer),
            isSelected = payment == PaymentMethod.BANK_TRANSFER,
            onClick = { onChooseMethod(PaymentMethod.BANK_TRANSFER) }
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
            .padding(horizontal = MaterialTheme.spacing.space16)
            .clickable { onClick() },
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