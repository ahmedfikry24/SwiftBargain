package com.example.swiftbargain.data.models

import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.Serializable

@Serializable
data class CouponCodeDto(
    val id: String = "",
    val code: String = "",

    @get:PropertyName("code_owner")
    @set:PropertyName("code_owner")
    var codeOwner: String = ""
)
