package com.example.swiftbargain.ui.home.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeUiState, HomeEvents>(HomeUiState()),
    HomeInteractions
