package com.example.swiftbargain.ui.home.compsable

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.PermissionRationaleDialog
import com.example.swiftbargain.ui.utils.getToSettings
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RememberNotificationPermission() {
    val context = LocalContext.current
    var isRationaleVisible by remember { mutableStateOf(false) }
    var isGoSettingsVisible by remember { mutableStateOf(false) }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val notificationPermission = rememberPermissionState(
            permission = android.Manifest.permission.POST_NOTIFICATIONS
        ) {
            isRationaleVisible = true
            isGoSettingsVisible = true
        }
        LaunchedEffect(Unit) {
            notificationPermission.launchPermissionRequest()
        }
        when {
            notificationPermission.status.isGranted -> Unit
            notificationPermission.status.shouldShowRationale -> {
                if (isRationaleVisible)
                    PermissionRationaleDialog(
                        message = stringResource(R.string.this_app_require_to_receive_notification_from_market_please_grant_the_permission),
                        onConfirm = { notificationPermission.launchPermissionRequest() },
                        onCancel = { isRationaleVisible = false },
                        onDismiss = { isRationaleVisible = false }
                    )
            }

            else -> {
                if (isGoSettingsVisible)
                    PermissionRationaleDialog(
                        message = stringResource(R.string.this_app_require_to_receive_notification_from_market_please_allow_access_in_the_settings),
                        onConfirm = {
                            isGoSettingsVisible = false
                            context.getToSettings()
                        },
                        onCancel = { isGoSettingsVisible = false },
                        onDismiss = { isGoSettingsVisible = false }
                    )
            }
        }
    }
}
