package com.example.swiftbargain.ui.favorites.view_model

import androidx.lifecycle.viewModelScope
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<FavoritesUiState, FavoritesEvents>(FavoritesUiState()), FavoritesInteractions {

    init {
        getFavorites()
    }

    override fun getFavorites() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        viewModelScope.launch {
            val favorites = repository.getAllFavorites()
            _state.update { value ->
                value.copy(
                    favorites = favorites.map { it.toUiState() },
                    contentStatus = ContentStatus.VISIBLE
                )
            }
        }
    }

    override fun onClickBack() {
        sendEvent(FavoritesEvents.NavigateToBack)
    }

    override fun onClickProduct(id: String) {
        sendEvent(FavoritesEvents.NavigateToProductDetails(id))
    }

    override fun onRemoveFavorite(id: String) {
        viewModelScope.launch {
            repository.removeFavoriteProduct(id)
            _state.update { value ->
                value.copy(
                    favorites = value.favorites.filterNot { it.id == id }
                )
            }
        }
    }
}
