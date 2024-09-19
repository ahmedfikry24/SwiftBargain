package com.example.swiftbargain.ui.profile.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.PrimaryTextButton
import com.example.swiftbargain.ui.composable.SecondaryTextField
import com.example.swiftbargain.ui.profile.view_model.ProfileInteractions
import com.example.swiftbargain.ui.profile.view_model.ProfileUiState
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EditProfile(
    modifier: Modifier = Modifier,
    state: ProfileUiState,
    interactions: ProfileInteractions
) {
    val transition =
        updateTransition(targetState = state.isEditProfileVisible, label = "transition")
    val localConfiguration = LocalConfiguration.current
    val height by transition.animateDp(label = "height") { if (it) localConfiguration.screenHeightDp.dp else 0.dp }
    val width by transition.animateDp(label = "width") { if (it) localConfiguration.screenWidthDp.dp else 0.dp }
    val background by transition.animateColor(label = "background") { if (it) MaterialTheme.colors.background else Color.Transparent }

    transition.AnimatedVisibility(
        modifier = modifier
            .size(width, height)
            .background(background),
        visible = { it },
        enter = slideInHorizontally(tween(500)) + fadeIn(tween(500)),
        exit = slideOutHorizontally(tween(500)) + fadeOut(tween(500))
    ) {
        ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
        ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
            val mediaPermission = rememberMediaPermission {}
            Column(modifier = modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = modifier.weight(1f),
                    contentPadding = PaddingValues(MaterialTheme.spacing.space16),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space24)
                ) {
                    item {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            PrimaryTextButton(
                                modifier = Modifier.align(Alignment.End),
                                text = stringResource(R.string.cancel),
                                containerColor = MaterialTheme.colors.background,
                                contentColor = MaterialTheme.colors.textGrey,
                                border = BorderStroke(1.dp, MaterialTheme.colors.textLight),
                                onClick = interactions::controlEditProfileVisibility
                            )
                        }
                    }

                    item {
                        EditProfileImage(
                            name = state.name,
                            imageUrl = state.imageUrl,
                            onClickEdit = { mediaPermission.launchPermissionRequest() }
                        )
                    }

                    item {
                        SecondaryTextField(
                            title = stringResource(R.string.full_name),
                            fieldValue = state.name,
                            isValueError = state.nameError,
                            onChangeValue = interactions::onChangeName
                        )
                    }

                    item {
                        EditProfileGender(
                            gender = state.gender,
                            isExpanded = state.isGenderDropDownVisible,
                            onSelect = interactions::onSelectGender,
                            controlVisibility = interactions::controlGenderDropDownVisibility
                        )
                    }

                    item {
                        EditProfileBirthday(
                            birthday = state.birthday,
                            onSelectDate = interactions::onSelectDate
                        )
                    }

                    item {
                        SecondaryTextField(
                            title = stringResource(R.string.phone),
                            fieldValue = state.phone,
                            isValueError = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                            onChangeValue = interactions::onChangePhone
                        )
                    }
                }

                PrimaryTextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.space16)
                        .padding(bottom = MaterialTheme.spacing.space16),
                    text = stringResource(R.string.save),
                    onClick = {}
                )
            }
        }
        ContentError(
            isVisible = state.contentStatus == ContentStatus.FAILURE,
            onTryAgain = {}
        )
    }
}
