package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.CreditUiSate

@Composable
fun CreditCardItem(
    modifier: Modifier = Modifier,
    card: CreditUiSate,
    background: Color = MaterialTheme.colors.primary,
    onClickItem: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(horizontal = MaterialTheme.spacing.space16),
        color = background,
        shape = RoundedCornerShape(MaterialTheme.spacing.space4),
        onClick = onClickItem
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.space20),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(
                            MaterialTheme.colors.textGrey.copy(alpha = .5f),
                            CircleShape
                        )
                )
                Box(
                    modifier = Modifier
                        .padding(start = MaterialTheme.spacing.space24)
                        .size(22.dp)
                        .background(
                            MaterialTheme.colors.textGrey.copy(alpha = .5f),
                            CircleShape
                        )

                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space20)
            ) {
                card.cardNum.chunked(4).forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colors.background
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space20)) {
                Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space4)) {
                    Text(
                        text = stringResource(R.string.card_holder_),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colors.background.copy(alpha = .5f)
                    )
                    Text(
                        text = card.holderName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colors.background
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space4)) {
                    Text(
                        text = stringResource(R.string.card_save),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colors.background.copy(alpha = .5f)
                    )
                    Text(
                        text = card.expiration,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colors.background
                    )
                }
            }
        }
    }
}