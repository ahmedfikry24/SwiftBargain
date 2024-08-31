package com.example.swiftbargain.ui.favorites.view_model

import com.example.swiftbargain.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() :
    BaseViewModel<FavoritesUiState, FavoritesEvents>(FavoritesUiState()), FavoritesInteractions
