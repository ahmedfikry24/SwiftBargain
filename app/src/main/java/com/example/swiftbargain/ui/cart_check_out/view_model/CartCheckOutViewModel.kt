package com.example.swiftbargain.ui.cart_check_out.view_model

import androidx.lifecycle.SavedStateHandle
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.ui.base.BaseViewModel
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

    override fun onClickBack() {

    }

    override fun controlAddAddressVisibility() {
        _state.update { it.copy(isAddAddressVisible = !it.isAddAddressVisible) }
    }


    // region add address

    override fun onChangeCountry(country: String) {
        _state.update { it.copy(addAddressUiState = it.addAddressUiState.copy(country = country)) }
    }

    override fun onChangeFirstName(name: String) {
        _state.update { it.copy(addAddressUiState = it.addAddressUiState.copy(firstName = name)) }
    }

    override fun onChangeLastName(name: String) {
        _state.update { it.copy(addAddressUiState = it.addAddressUiState.copy(lastname = name)) }
    }

    override fun onChangeStreetAddress(address: String) {
        _state.update { it.copy(addAddressUiState = it.addAddressUiState.copy(streetAddress = address)) }
    }

    override fun onChangeStreetAddress2(address: String) {
        _state.update { it.copy(addAddressUiState = it.addAddressUiState.copy(streetAddress2 = address)) }
    }

    override fun onChangeCity(city: String) {
        _state.update { it.copy(addAddressUiState = it.addAddressUiState.copy(city = city)) }
    }

    override fun onChangeRegion(region: String) {
        _state.update { it.copy(addAddressUiState = it.addAddressUiState.copy(region = region)) }
    }

    override fun onChangeZipCode(code: String) {
        _state.update { it.copy(addAddressUiState = it.addAddressUiState.copy(zipCode = code)) }
    }

    override fun onChangePhone(phone: String) {
        _state.update { it.copy(addAddressUiState = it.addAddressUiState.copy(phone = phone)) }
    }

    override fun onClickAddAddress() {
        if (validateAddAddress()) {

        }
    }

    private fun validateAddAddress(): Boolean {
        val value = state.value.addAddressUiState
        val country = value.country.validateRequireFields()
        val firstname = value.firstName.validateRequireFields()
        val lastname = value.lastname.validateRequireFields()
        val streetAddress = value.streetAddress.validateRequireFields()
        val city = value.city.validateRequireFields()
        val region = value.region.validateRequireFields()
        val code = value.zipCode.validateRequireFields()
        val phone = value.phone.validateRequireFields()
        val hasError = listOf(
            country,
            firstname,
            lastname,
            streetAddress,
            code,
            city,
            region,
            phone
        ).any { !it }
        _state.update {
            it.copy(
                addAddressUiState = it.addAddressUiState.copy(
                    countryError = !country,
                    firstNameError = !firstname,
                    lastnameError = !lastname,
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
