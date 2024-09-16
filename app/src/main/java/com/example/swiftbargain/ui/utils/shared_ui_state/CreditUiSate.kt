package com.example.swiftbargain.ui.utils.shared_ui_state

import com.example.swiftbargain.data.local.room.entity.CreditEntity
import com.example.swiftbargain.ui.utils.ContentStatus

data class CreditUiSate(
    val contentStatus: ContentStatus = ContentStatus.VISIBLE,
    val cardNum: String = "",
    val cardNumError: Boolean = false,
    val expiration: String = "",
    val expirationError: Boolean = false,
    val securityCode: String = "",
    val securityCodeError: Boolean = false,
    val holderName: String = "",
    val holderNameError: Boolean = false
)


fun CreditUiSate.toEntity(): CreditEntity {
    return CreditEntity(
        cardNumber = this.cardNum,
        expiration = this.expiration,
        securityCode = this.securityCode,
        holderName = this.holderName
    )
}

fun CreditEntity.toUiState(): CreditUiSate {
    return CreditUiSate(
        cardNum = this.cardNumber,
        expiration = this.expiration,
        securityCode = this.securityCode,
        holderName = this.holderName
    )
}

