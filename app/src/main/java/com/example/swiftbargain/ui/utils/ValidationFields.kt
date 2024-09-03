package com.example.swiftbargain.ui.utils

fun String.validateEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches() && this.isNotBlank()
}

fun String.validatePassword(): Boolean {
    return this.length >= 6 && this.isNotBlank()
}

fun String.validateRequireFields(): Boolean {
    return this.isNotBlank()
}
