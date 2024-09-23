package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun SecondaryAppBar(
    modifier: Modifier = Modifier,
    title: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp, max = 56.dp),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)
    ) {
        Text(
            modifier = Modifier.padding(MaterialTheme.spacing.space16),
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colors.text
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colors.textGrey
        )
    }
}
