package com.example.swiftbargain.ui.profile.composable

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.PermissionRationaleDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberMediaPermission(onGranted: () -> Unit): PermissionState {
    val context = LocalContext.current
    var isRationaleVisible by remember { mutableStateOf(false) }
    var isGoSettingsVisible by remember { mutableStateOf(false) }
    var isGrantedActionDone by rememberSaveable { mutableStateOf(false) }
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        android.Manifest.permission.READ_MEDIA_IMAGES
    else
        android.Manifest.permission.READ_EXTERNAL_STORAGE

    val mediaPermissionState = rememberPermissionState(permission) {
        isGrantedActionDone = it
        isRationaleVisible = true
        isGoSettingsVisible = true
    }
    when {
        mediaPermissionState.status.isGranted -> {
            if (isGrantedActionDone)
                onGranted()
            isGrantedActionDone = false
        }

        mediaPermissionState.status.shouldShowRationale -> {
            if (isRationaleVisible)
                PermissionRationaleDialog(
                    message = stringResource(R.string.this_app_requires_media_access_to_update_profile_image_please_grant_the_permission),
                    onConfirm = { mediaPermissionState.launchPermissionRequest() },
                    onCancel = { isRationaleVisible = false },
                    onDismiss = { isRationaleVisible = false }
                )
        }

        else -> {
            if (isGoSettingsVisible)
                PermissionRationaleDialog(
                    message = stringResource(R.string.this_app_needs_media_access_for_update_profile_image_please_allow_access_in_the_settings),
                    onConfirm = {
                        isGoSettingsVisible = false
                        context.getToSettings()
                    },
                    onCancel = { isGoSettingsVisible = false },
                    onDismiss = { isGoSettingsVisible = false }
                )
        }
    }


    return mediaPermissionState
}

private fun Context.getToSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", this.packageName, null)
    ).also(::startActivity)
}