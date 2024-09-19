package com.example.swiftbargain.ui.profile.view_model

import android.net.Uri
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.UserInfoUiState

data class ProfileUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val profileInfo: UserInfoUiState = UserInfoUiState(),
    val isEditProfileVisible: Boolean = false,
    val isSaveInfoDialogVisible: Boolean = false,
    val updateProfile: UserInfoUiState = UserInfoUiState(),
    val updateImageProfile: Uri? = null,
    val isGenderDropDownVisible: Boolean = false,
)