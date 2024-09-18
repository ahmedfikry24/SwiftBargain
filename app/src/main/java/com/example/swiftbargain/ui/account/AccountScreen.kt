package com.example.swiftbargain.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.navigation.Addresses
import com.example.swiftbargain.navigation.Orders
import com.example.swiftbargain.navigation.Payments
import com.example.swiftbargain.navigation.Profile
import com.example.swiftbargain.ui.account.view_model.AccountEvents
import com.example.swiftbargain.ui.account.view_model.AccountInteractions
import com.example.swiftbargain.ui.account.view_model.AccountViewModel
import com.example.swiftbargain.ui.composable.SecondaryAppBar
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    EventHandler(effects = viewModel.event) { event, _ ->
        when (event) {
            AccountEvents.NavigateToAddress -> navController.navigate(Addresses)
            AccountEvents.NavigateToOrders -> navController.navigate(Orders)
            AccountEvents.NavigateToPayment -> navController.navigate(Payments)
            AccountEvents.NavigateToProfile -> navController.navigate(Profile)
        }
    }
    AccountContent(interactions = viewModel)
}

@Composable
private fun AccountContent(interactions: AccountInteractions) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
    ) {
        SecondaryAppBar(title = stringResource(R.string.account))
        SectionItem(
            iconId = R.drawable.ic_profile,
            text = stringResource(R.string.profile),
            onClick = interactions::onClickProfile
        )
        SectionItem(
            iconId = R.drawable.ic_order,
            text = stringResource(R.string.orders),
            onClick = interactions::onClickOrder
        )
        SectionItem(
            iconId = R.drawable.ic_location,
            text = stringResource(R.string.address),
            onClick = interactions::onClickAddress
        )
        SectionItem(
            iconId = R.drawable.ic_credit_card,
            text = stringResource(R.string.payment),
            onClick = interactions::onClickPayment
        )
    }
}


@Composable
private fun SectionItem(
    modifier: Modifier = Modifier,
    iconId: Int,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = MaterialTheme.colors.textGrey)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)) {
            Icon(
                modifier = Modifier.padding(start = MaterialTheme.spacing.space16),
                imageVector = ImageVector.vectorResource(iconId),
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colors.text
            )
        }
    }
}
