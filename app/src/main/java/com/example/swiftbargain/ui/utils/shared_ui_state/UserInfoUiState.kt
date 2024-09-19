package com.example.swiftbargain.ui.utils.shared_ui_state

import com.example.swiftbargain.data.models.UserInfoDto
import com.example.swiftbargain.ui.utils.ContentStatus

data class UserInfoUiState(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val nameError: Boolean = false,
    val email: String = "",
    val gender: String = "",
    val birthday: String = "",
    val phone: String = "",
    val address: List<AddressUiState> = listOf()
)


fun UserInfoDto.toUiState(): UserInfoUiState {
    return UserInfoUiState(
        name = this.name ?: "",
        email = this.email ?: "",
        imageUrl = this.imageUrl ?: "",
        gender = this.gender,
        birthday = this.birthday,
        phone = this.phone ?: "",
        address = this.addresses.map { it.toUiState() }
    )
}

fun UserInfoUiState.toDto(): UserInfoDto {
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
