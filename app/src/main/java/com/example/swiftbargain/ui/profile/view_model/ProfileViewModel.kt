package com.example.swiftbargain.ui.profile.view_model

import com.example.swiftbargain.data.models.UserInfoDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel<ProfileUiState, ProfileEvents>(ProfileUiState()), ProfileInteractions {

    init {
        getProfileInfo()
    }

    override fun getProfileInfo() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.getUserInfo() },
            ::profileSuccess,
            ::profileError
        )
    }

    private fun profileSuccess(info: UserInfoDto) {
        _state.value = info.toUiState()
        _state.update { it.copy(contentStatus = ContentStatus.VISIBLE) }
    }

    private fun profileError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }
}
