package com.example.swiftbargain.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.PrimaryAppbar
import com.example.swiftbargain.ui.profile.composable.EditProfile
import com.example.swiftbargain.ui.profile.composable.ProfileImage
import com.example.swiftbargain.ui.profile.view_model.ProfileInteractions
import com.example.swiftbargain.ui.profile.view_model.ProfileUiState
import com.example.swiftbargain.ui.profile.view_model.ProfileViewModel
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler

@Composable
fun ProfileScreen(
    navController: NavController,
    unAuthorizedLogin: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->

    }
    ProfileContent(state = state, interactions = viewModel)
}

@Composable
private fun ProfileContent(
    state: ProfileUiState,
    interactions: ProfileInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = MaterialTheme.spacing.space16),
        ) {
            item {
                PrimaryAppbar(
                    title = stringResource(R.string.profile),
                    onClickBack = {}
                )
            }
            item {
                ProfileImage(
                    state = state,
                    onClickEdit = interactions::controlEditProfileVisibility
                )
            }
            item {
                InfoSection(
                    iconId = R.drawable.ic_gender,
                    title = stringResource(R.string.gender),
                    value = state.gender
                )
            }
            if (state.birthday.isNotEmpty())
                item {
                    InfoSection(
                        iconId = R.drawable.ic_calender,
                        title = stringResource(R.string.birthday),
                        value = state.birthday
                    )
                }

            if (state.phone.isNotEmpty())
                item {
                    InfoSection(
                        iconId = R.drawable.ic_phone,
                        title = stringResource(R.string.phone),
                        value = state.phone
                    )
                }
        }
        EditProfile(state = state, interactions = interactions)
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = interactions::getProfileInfo
    )
}


@Composable
private fun InfoSection(
    modifier: Modifier = Modifier,
    iconId: Int,
    title: String,
    value: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.spacing.space16)
            .height(56.dp)
            .padding(horizontal = MaterialTheme.spacing.space16),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.space16),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16)) {
                Icon(
                    modifier = Modifier.padding(end = MaterialTheme.spacing.space16),
                    imageVector = ImageVector.vectorResource(iconId),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colors.text
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colors.textGrey
            )
        }

    }
}
