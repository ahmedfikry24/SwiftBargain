package com.example.swiftbargain.ui.profile.view_model

import com.example.swiftbargain.data.models.UserInfoDto
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.toDto
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState

data class ProfileUiState(
    val contentStatus: ContentStatus = ContentStatus.LOADING,
    val isEditProfileVisible: Boolean = false,
    val updateContentStatus: ContentStatus = ContentStatus.VISIBLE,
    val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val nameError: Boolean = false,
    val email: String = "",
    val gender: String = "",
    val isGenderDropDownVisible: Boolean = false,
    val birthday: String = "",
    val phone: String = "",
    val address: List<AddressUiState> = listOf()
)


fun UserInfoDto.toUiState(): ProfileUiState {
    return ProfileUiState(
        name = this.name ?: "",
        email = this.email ?: "",
        imageUrl = this.imageUrl ?: "",
        gender = this.gender,
        birthday = this.birthday,
        phone = this.phone ?: "",
        address = this.addresses.map { it.toUiState() }
    )
}

fun ProfileUiState.toDto(): UserInfoDto {
    return UserInfoDto(
        name = this.name,
        email = this.email,
        imageUrl = this.imageUrl,
        gender = this.gender,
        birthday = this.birthday,
        phone = this.phone,
        addresses = this.address.map { it.toDto() }
    )
}