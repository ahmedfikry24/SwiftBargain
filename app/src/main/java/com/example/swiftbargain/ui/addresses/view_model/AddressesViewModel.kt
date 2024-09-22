package com.example.swiftbargain.ui.addresses.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor() :
    BaseViewModel<AddressesUiState, AddressesEvents>(AddressesUiState()), AddressesInteractions
