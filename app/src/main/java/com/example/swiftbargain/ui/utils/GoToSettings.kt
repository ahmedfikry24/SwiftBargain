package com.example.swiftbargain.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Context.getToSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", this.packageName, null)
    ).also(::startActivity)
}