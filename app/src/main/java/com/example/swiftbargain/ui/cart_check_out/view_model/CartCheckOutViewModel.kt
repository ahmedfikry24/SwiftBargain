package com.example.swiftbargain.ui.cart_check_out.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.swiftbargain.data.models.UserInfoDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.CartCheckOut
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.AddAddressUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.toDto
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import com.example.swiftbargain.ui.utils.validateRequireFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CartCheckOutViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel<CartCheckOutUiState, CartCheckOutEvents>(CartCheckOutUiState()),
    CartCheckOutInteractions {
    private val args = savedStateHandle.toRoute<CartCheckOut>()

    init {
        getAllAddress()
    }

    override fun getAllAddress() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.getUserAddress() },
            ::allAddressSuccess,
            ::allAddressError
        )
    }

    private fun allAddressSuccess(address: List<UserInfoDto.AddressInfo>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                allAddresses = address.map { it.toUiState() }
            )
        }
    }

    private fun allAddressError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onClickBack() {

    }

    override fun controlAddAddressVisibility() {
        _state.update { it.copy(isAddAddressVisible = !it.isAddAddressVisible) }
    }

    override fun onSelectAddress(address: AddAddressUiState) {
        _state.update { it.copy(selectedAddress = address) }
    }

    override fun onDeleteAddress(address: AddAddressUiState) {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.deleteUserAddress(address.toDto()) },
            { deleteAddressSuccess(address) },
            ::deleteAddressError
        )
    }

    private fun deleteAddressSuccess(address: AddAddressUiState) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                allAddresses = value.allAddresses.filter { it != address }
            )
        }
    }

    private fun deleteAddressError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    // region add address

    override fun onChangeCountry(country: String) {
        _state.update { it.copy(addAddressState = it.addAddressState.copy(country = country)) }
    }

    override fun onChangeName(name: String) {
        _state.update { it.copy(addAddressState = it.addAddressState.copy(name = name)) }
    }

    override fun onChangeStreetAddress(address: String) {
        _state.update { it.copy(addAddressState = it.addAddressState.copy(streetAddress = address)) }
    }

    override fun onChangeStreetAddress2(address: String) {
        _state.update { it.copy(addAddressState = it.addAddressState.copy(streetAddress2 = address)) }
    }

    override fun onChangeCity(city: String) {
        _state.update { it.copy(addAddressState = it.addAddressState.copy(city = city)) }
    }

    override fun onChangeRegion(region: String) {
        _state.update { it.copy(addAddressState = it.addAddressState.copy(region = region)) }
    }

    override fun onChangeZipCode(code: String) {
        _state.update { it.copy(addAddressState = it.addAddressState.copy(zipCode = code)) }
    }

    override fun onChangePhone(phone: String) {
        _state.update { it.copy(addAddressState = it.addAddressState.copy(phone = phone)) }
    }

    override fun onClickAddAddress() {
        if (validateAddAddress()) {
            _state.update { it.copy(addAddressState = it.addAddressState.copy(contentStatus = ContentStatus.LOADING)) }
            tryExecute(
                { repository.addUserAddressInfo(state.value.addAddressState.toDto()) },
                { addressSuccess() },
                ::addressError
            )
        }
    }

    private fun addressSuccess() {
        _state.update {
            it.copy(
                addAddressState = it.addAddressState.copy(contentStatus = ContentStatus.VISIBLE),
                isAddAddressVisible = false,
            )
        }
        _state.update { it.copy(addAddressState = AddAddressUiState()) }
    }

    private fun addressError(error: BaseError) {
        _state.update { it.copy(addAddressState = it.addAddressState.copy(contentStatus = ContentStatus.FAILURE)) }
    }

    private fun validateAddAddress(): Boolean {
        val value = state.value.addAddressState
        val country = value.country.validateRequireFields()
        val name = value.name.validateRequireFields()
        val streetAddress = value.streetAddress.validateRequireFields()
        val city = value.city.validateRequireFields()
        val region = value.region.validateRequireFields()
        val code = value.zipCode.validateRequireFields()
        val phone = value.phone.validateRequireFields()
        val hasError = listOf(country, name, streetAddress, code, city, region, phone).any { !it }
        _state.update {
            it.copy(
                addAddressState = it.addAddressState.copy(
                    countryError = !country,
                    nameError = !name,
                    streetAddressError = !streetAddress,
                    cityError = !city,
                    regionError = !region,
                    zipCodeError = !code,
                    phoneError = !phone
                )
            )
        }

        return !hasError
    }
    // endregion


}
