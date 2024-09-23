package com.example.swiftbargain.ui.profile.composable

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import coil.compose.rememberAsyncImagePainter
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing

@Composable
fun EditProfileImage(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    imageUri: Uri?,
    onClickEdit: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl.isEmpty() && imageUri == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = name.first().uppercase(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colors.background
                        )
                    }
                } else if (imageUrl.isEmpty() && imageUri != null) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                } else {
                    SubcomposeAsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                    ) {
                        if (painter.state is AsyncImagePainter.State.Loading)
                            CircularProgressIndicator(color = MaterialTheme.colors.primary)
                        else SubcomposeAsyncImageContent()
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(start = MaterialTheme.spacing.space20)
                    .size(36.dp)
                    .background(
                        MaterialTheme.colors.textGrey.copy(alpha = .5f),
                        CircleShape
                    )
                    .clip(CircleShape)
                    .clickable { onClickEdit() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_pin_edit),
                    contentDescription = null,
                    tint = MaterialTheme.colors.background.copy(alpha = .8f)
                )
            }
        }
    }
}
