package com.example.swiftbargain.ui.explore.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor() :
    BaseViewModel<ExploreUiState, ExploreEvents>(ExploreUiState()), ExploreInteractions
