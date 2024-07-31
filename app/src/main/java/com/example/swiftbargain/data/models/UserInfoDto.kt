package com.example.swiftbargain.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoDto(
    val id: String,
    val name: String?,
    val email: String?
) : Parcelable
