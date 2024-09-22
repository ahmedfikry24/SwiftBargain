package com.example.swiftbargain.ui.addresses.view_model

import com.example.swiftbargain.data.models.AddressDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.toDto
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import com.example.swiftbargain.ui.utils.validateRequireFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel<AddressesUiState, AddressesEvents>(AddressesUiState()), AddressesInteractions {

    init {
        getAllAddresses()
    }

    override fun getAllAddresses() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            { repository.getUserAddress() },
            ::addressesSuccess,
            ::addressesError
        )
    }

    private fun addressesSuccess(addresses: List<AddressDto>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                allAddresses = addresses.map { it.toUiState() }
            )
        }
    }

    private fun addressesError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onCLickBack() {

    }

    override fun controlAddAddressVisibility() {
        _state.update { it.copy(isAddAddressVisible = !it.isAddAddressVisible) }
    }

    // region address
    override fun onChangeCountry(country: String) {
        _state.update { it.copy(addAddress = it.addAddress.copy(country = country)) }
    }

    override fun onChangeName(name: String) {
        _state.update { it.copy(addAddress = it.addAddress.copy(name = name)) }
    }

    override fun onChangeStreetAddress(address: String) {
        _state.update { it.copy(addAddress = it.addAddress.copy(streetAddress = address)) }
    }

    override fun onChangeStreetAddress2(address: String) {
        _state.update { it.copy(addAddress = it.addAddress.copy(streetAddress2 = address)) }
    }

    override fun onChangeCity(city: String) {
        _state.update { it.copy(addAddress = it.addAddress.copy(city = city)) }
    }

    override fun onChangeZipCode(code: String) {
        _state.update { it.copy(addAddress = it.addAddress.copy(zipCode = code)) }
    }

    override fun onChangePhone(phone: String) {
        _state.update { it.copy(addAddress = it.addAddress.copy(phone = phone)) }
    }

    override fun onClickAddAddress() {
        if (validateAddAddress()) {
            _state.update { it.copy(addAddress = it.addAddress.copy(contentStatus = ContentStatus.LOADING)) }
            tryExecute(
                { repository.addUserAddressInfo(state.value.addAddress.toDto()) },
                { addressSuccess() },
                ::addressError
            )
        }
    }

    private fun addressSuccess() {
        _state.update {
            it.copy(
                addAddress = it.addAddress.copy(contentStatus = ContentStatus.VISIBLE),
                isAddAddressVisible = false,
                allAddresses = it.allAddresses.toMutableList().apply { add(it.addAddress) }
            )
        }
        _state.update { it.copy(addAddress = AddressUiState()) }
    }

    private fun addressError(error: BaseError) {
        _state.update { it.copy(addAddress = it.addAddress.copy(contentStatus = ContentStatus.FAILURE)) }
    }

    private fun validateAddAddress(): Boolean {
        val value = state.value.addAddress
        val country = value.country.validateRequireFields()
        val name = value.name.validateRequireFields()
        val streetAddress = value.streetAddress.validateRequireFields()
        val city = value.city.validateRequireFields()
        val code = value.zipCode.validateRequireFields()
        val phone = value.phone.validateRequireFields()
        val hasError = listOf(country, name, streetAddress, code, city, phone).any { !it }
        _state.update {
            it.copy(
                addAddress = it.addAddress.copy(
                    countryError = !country,
                    nameError = !name,
                    streetAddressError = !streetAddress,
                    cityError = !city,
                    zipCodeError = !code,
                    phoneError = !phone
                )
            )
        }

        return !hasError
    }

// endregion

    override fun controlDeleteAddressDialogVisibility() {
        _state.update { it.copy(isDeleteAddressVisible = !it.isDeleteAddressVisible) }
    }

    override fun onSelectDeleteAddress(address: AddressUiState) {
        _state.update { it.copy(selectedAddress = address) }
    }

    override fun onDeleteAddress() {
        _state.update {
            it.copy(
                contentStatus = ContentStatus.LOADING,
                isDeleteAddressVisible = false
            )
        }
        tryExecute(
            { repository.deleteUserAddress(state.value.selectedAddress.toDto()) },
            { deleteAddressSuccess() },
            ::deleteAddressError
        )
    }

    private fun deleteAddressSuccess() {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                allAddresses = value.allAddresses.filter { it != value.selectedAddress }
            )
        }
    }

    private fun deleteAddressError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }
}
