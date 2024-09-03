package com.example.swiftbargain.ui.explore.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
fun rememberMicPermission(
    onGranted: () -> Unit,
    goToSettings: () -> Unit,
): PermissionState {
    var isRationaleVisible by remember { mutableStateOf(false) }
    var isGoSettingsVisible by remember { mutableStateOf(false) }
    var isGrantedActionDone by rememberSaveable { mutableStateOf(false) }
    val micPermission = rememberPermissionState(android.Manifest.permission.RECORD_AUDIO) {
        isRationaleVisible = true
        isGoSettingsVisible = true
        isGrantedActionDone = it
    }

    when {
        micPermission.status.isGranted -> {
            if (isGrantedActionDone)
                onGranted()
            isGrantedActionDone = false
        }

        micPermission.status.shouldShowRationale -> {
            if (isRationaleVisible)
                PermissionRationaleDialog(
                    message = stringResource(R.string.this_app_requires_microphone_access_to_perform_voice_searches_please_grant_the_permission),
                    onConfirm = { micPermission.launchPermissionRequest() },
                    onCancel = { isRationaleVisible = false },
                    onDismiss = { isRationaleVisible = false }
                )
        }

        else -> {
            if (isGoSettingsVisible)
                PermissionRationaleDialog(
                    message = stringResource(R.string.this_app_needs_microphone_access_for_voice_search_please_allow_access_in_the_settings),
                    onConfirm = {
                        isGoSettingsVisible = false
                        goToSettings()
                    },
                    onCancel = { isGoSettingsVisible = false },
                    onDismiss = { isGoSettingsVisible = false }
                )
        }
    }
    return micPermission
}
