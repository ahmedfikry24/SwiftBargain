package com.example.swiftbargain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftbargain.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    init {
        getLoginStatus()
    }

    private fun getLoginStatus() {
        viewModelScope.launch {
            repository.getIsLogin().collect { isLogin ->
                _state.update { it.copy(isLoading = false, isLogin = isLogin) }
            }
        }
    }

    data class MainUiState(
        val isLoading: Boolean = true,
        val isLogin: Boolean = false
    )

}
