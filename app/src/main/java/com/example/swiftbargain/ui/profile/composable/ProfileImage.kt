package com.example.swiftbargain.ui.profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.profile.view_model.ProfileUiState
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    state: ProfileUiState,
    onClickEdit: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.spacing.space16)
            .padding(horizontal = MaterialTheme.spacing.space16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (state.profileInfo.imageUrl.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.profileInfo.name.first().uppercase(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colors.background
                    )
                }
            } else {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = state.profileInfo.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                ) {
                    if (painter.state is AsyncImagePainter.State.Loading)
                        CircularProgressIndicator(color = MaterialTheme.colors.primary)
                    else SubcomposeAsyncImageContent()
                }
            }
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space4)
        ) {
            Text(
                text = state.profileInfo.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colors.text
            )
            Text(
                text = state.profileInfo.email,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colors.textGrey
            )
        }
        IconButton(
            modifier = Modifier.padding(start = MaterialTheme.spacing.space16),
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colors.text),
            onClick = onClickEdit
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_pin_edit),
                contentDescription = null
            )
        }
    }
}
