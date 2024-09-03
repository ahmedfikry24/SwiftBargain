package com.example.swiftbargain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftbargain.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var userUid = ""
    val scope = CoroutineScope(Dispatchers.Default)

    fun getLoginStatus() {
        viewModelScope.launch {
            scope.launch {
                repository.getUserUid().collect { uid ->
                    userUid = uid
                }
            }
        }
    }
}
