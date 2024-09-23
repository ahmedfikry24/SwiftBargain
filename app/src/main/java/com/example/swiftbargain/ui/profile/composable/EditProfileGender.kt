package com.example.swiftbargain.ui.profile.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun EditProfileGender(
    modifier: Modifier = Modifier,
    gender: String,
    isExpanded: Boolean,
    onSelect: (String) -> Unit,
    controlVisibility: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
    ) {
        Text(
            text = stringResource(R.string.gender),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colors.text
        )

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp, max = 56.dp)
                    .clickable(
                        onClick = controlVisibility,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = MaterialTheme.colors.textLight)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = gender,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colors.textGrey
                )
                Icon(
                    modifier = Modifier.rotate(if (isExpanded) 90f else 270f),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_bcak),
                    contentDescription = null,
                    tint = MaterialTheme.colors.textGrey
                )
            }

            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colors.textGrey),
                expanded = isExpanded,
                containerColor = MaterialTheme.colors.background,
                onDismissRequest = controlVisibility
            ) {
                DropdownMenuItem(
                    contentPadding = PaddingValues(MaterialTheme.spacing.space8),
                    text = {
                        Text(
                            text = stringResource(R.string.male),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colors.textGrey
                        )
                    },
                    onClick = { onSelect("Male") }
                )
                DropdownMenuItem(
                    contentPadding = PaddingValues(MaterialTheme.spacing.space8),
                    text = {
                        Text(
                            text = stringResource(R.string.female),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colors.textGrey
                        )
                    },
                    onClick = { onSelect("Female") }
                )
            }
        }
    }
}
