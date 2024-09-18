package com.example.swiftbargain.ui.profile.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() :
    BaseViewModel<ProfileUiState, ProfileEvents>(ProfileUiState()), ProfileInteractions
