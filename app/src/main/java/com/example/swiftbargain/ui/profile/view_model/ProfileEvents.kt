package com.example.swiftbargain.ui.profile.view_model

sealed interface ProfileEvents {
    data object NavigateToBack : ProfileEvents
    data object UnAuthorizedAccess : ProfileEvents
    data object UpdateProfileSuccess : ProfileEvents
}
