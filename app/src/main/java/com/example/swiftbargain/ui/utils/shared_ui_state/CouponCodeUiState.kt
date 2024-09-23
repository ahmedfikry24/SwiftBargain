package com.example.swiftbargain.ui.utils.shared_ui_state

import com.example.swiftbargain.data.models.CouponCodeDto

data class CouponCodeUiState(
    val id: String = "",
    val code: String = "",
    val discount: String = "",
    val codeOwner: String = ""
)


fun CouponCodeDto.toUiState(): CouponCodeUiState {
    return CouponCodeUiState(
        id = this.id,
        code = this.code,
        discount = this.discount,
        codeOwner = this.codeOwner
    )
}