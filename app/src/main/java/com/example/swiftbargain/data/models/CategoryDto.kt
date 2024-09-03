package com.example.swiftbargain.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDto(
    val id: String = "",
    val label: String = "",
    val url: String = ""
) : Parcelable
