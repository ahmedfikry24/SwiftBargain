package com.example.swiftbargain.ui.register.viiew_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() :
    BaseViewModel<RegisterUiState, RegisterEvents>(RegisterUiState()), RegisterInteractions
