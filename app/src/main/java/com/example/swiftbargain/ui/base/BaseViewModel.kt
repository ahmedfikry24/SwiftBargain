package com.example.swiftbargain.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<U, E>(uiState: U) : ViewModel() {
    protected val _state = MutableStateFlow(uiState)
    val state = _state.asStateFlow()

    protected val _event = MutableSharedFlow<E>()
    val event = _event.asSharedFlow()

    fun <T> tryExecute(
        progress: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (error: BaseError) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                val executable = progress()
                onSuccess(executable)
            } catch (e: UserNotFound) {
                onError(UserNotFound())
            } catch (e: EmailIsNoVerified) {
                onError(EmailIsNoVerified())
            } catch (e: EmailIsAlreadyUsed) {
                onError(EmailIsAlreadyUsed())
            } catch (e: NoInternetConnection) {
                onError(NoInternetConnection())
            } catch (e: SomethingWentWrong) {
                onError(SomethingWentWrong())
            }
        }
    }

    fun sendEvent(event: E) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}
