package com.example.swiftbargain.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SaleAdDto(
    val id: String = "",
    val title: String = "",
    val url: String = ""
) : Parcelable
