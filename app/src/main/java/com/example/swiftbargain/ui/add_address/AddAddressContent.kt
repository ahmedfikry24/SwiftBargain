package com.example.swiftbargain.ui.add_address

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.AddAddressUiState
import com.example.swiftbargain.ui.utils.shred_interactions.AddAddressInteractions

@Composable
fun AddAddressContent(
    modifier: Modifier = Modifier,
    state: AddAddressUiState,
    interactions: AddAddressInteractions
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.spacing.space16),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
    ) {
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                PrimaryTextButton(
                    modifier = Modifier.align(Alignment.End),
                    text = stringResource(R.string.cancel),
                    containerColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.textGrey,
                    border = BorderStroke(1.dp, MaterialTheme.colors.textLight),
                    onClick = {}
                )
            }
        }
    }
}
