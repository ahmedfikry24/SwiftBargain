package com.example.swiftbargain.ui.sale.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.Sale
import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SaleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel<SaleUiState, SaleEvents>(SaleUiState()), SaleInteractions {

    private val args = savedStateHandle.toRoute<Sale>()
}
