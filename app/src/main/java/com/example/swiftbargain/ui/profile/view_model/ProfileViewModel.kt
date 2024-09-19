package com.example.swiftbargain.ui.profile.view_model

import android.net.Uri
import com.example.swiftbargain.data.models.UserInfoDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toDto
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import com.example.swiftbargain.ui.utils.validateRequireFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<ProfileUiState, ProfileEvents>(ProfileUiState()), ProfileInteractions {

    init {
        getProfileInfo()
    }

    override fun getProfileInfo() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.getUserInfo() },
            ::profileSuccess,
            ::profileError
        )
    }

    private fun profileSuccess(info: UserInfoDto) {
        _state.update {
            it.copy(
                contentStatus = ContentStatus.VISIBLE,
                profileInfo = info.toUiState(),
                updateProfile = info.toUiState()
            )
        }
    }

    private fun profileError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun controlEditProfileVisibility() {
        _state.update { it.copy(isEditProfileVisible = !it.isEditProfileVisible) }
    }

    override fun onCancelEdit() {
        val isTheSame = checkIsUpdatedInfoSameMain()
        _state.update {
            it.copy(
                isEditProfileVisible = !isTheSame,
                isSaveInfoDialogVisible = !isTheSame
            )
        }
    }

    override fun onSelectImage(image: Uri) {
        _state.update { it.copy(updateImageProfile = image) }
    }

    override fun onChangeName(name: String) {
        _state.update { it.copy(updateProfile = it.updateProfile.copy(name = name)) }
    }

    override fun controlGenderDropDownVisibility() {
        _state.update { it.copy(isGenderDropDownVisible = !it.isGenderDropDownVisible) }
    }

    override fun onSelectGender(gender: String) {
        _state.update {
            it.copy(
                updateProfile = it.updateProfile.copy(gender = gender),
                isGenderDropDownVisible = false
            )
        }
    }

    override fun onSelectDate(date: String) {
        _state.update { it.copy(updateProfile = it.updateProfile.copy(birthday = date)) }
    }

    override fun onChangePhone(phone: String) {
        _state.update { it.copy(updateProfile = it.updateProfile.copy(phone = phone)) }
    }

    private fun checkIsUpdatedInfoSameMain(): Boolean {
        val mainInfo = state.value.profileInfo
        val updatedInfo = state.value.updateProfile

        val name = mainInfo.name == updatedInfo.name
        val gender = mainInfo.gender == updatedInfo.gender
        val birthday = mainInfo.birthday == updatedInfo.birthday
        val phone = mainInfo.phone == updatedInfo.phone
        val isSame = listOf(name, gender, birthday, phone).any { false }

        return !isSame
    }

    override fun controlSaveChangesDialogVisibility() {
        _state.update { it.copy(isSaveInfoDialogVisible = !it.isSaveInfoDialogVisible) }
    }

    override fun updateProfile() {
        val value = state.value
        val nameValidation = value.updateProfile.name.validateRequireFields()
        _state.update { it.copy(updateProfile = it.updateProfile.copy(nameError = !nameValidation)) }
        if (nameValidation) {
            _state.update { it.copy(updateProfile = it.updateProfile.copy(contentStatus = ContentStatus.LOADING)) }
            tryExecute(
                {
                    repository.updateProfileInfo(
                        value.updateProfile.toDto(),
                        value.updateImageProfile
                    )
                },
                ::updateSuccess,
                ::updateError
            )
        }
    }

    private fun updateSuccess(imageUrl: String) {
        _state.update {
            it.copy(
                updateProfile = it.updateProfile.copy(contentStatus = ContentStatus.VISIBLE),
                isEditProfileVisible = false,
                profileInfo = it.updateProfile.copy(imageUrl = imageUrl),
            )
        }
    }

    private fun updateError(error: BaseError) {
        _state.update { it.copy(updateProfile = it.updateProfile.copy(contentStatus = ContentStatus.FAILURE)) }
    }
}
