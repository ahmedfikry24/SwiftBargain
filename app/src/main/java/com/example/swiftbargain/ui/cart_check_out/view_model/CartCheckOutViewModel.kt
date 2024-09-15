package com.example.swiftbargain.ui.cart_check_out.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.swiftbargain.data.models.UserInfoDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.CartCheckOut
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.PaymentMethod
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

    override fun onSelectAddress(address: AddressUiState) {
        _state.update { it.copy(selectedAddress = address) }
    }

    override fun onDeleteAddress(address: AddressUiState) {
        _state.update {
            it.copy(
                selectedDeleteAddress = address,
                isDeleteAddressVisible = true
            )
        }

    }

    override fun deleteAddress() {
        _state.update {
            it.copy(
                contentStatus = ContentStatus.LOADING,
                isDeleteAddressVisible = false
            )
        }
        tryExecute(
            { repository.deleteUserAddress(state.value.selectedDeleteAddress.toDto()) },
            { deleteAddressSuccess() },
            ::deleteAddressError
        )
    }

    private fun deleteAddressSuccess() {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                allAddresses = value.allAddresses.filter { it != value.selectedDeleteAddress }
            )
        }
    }

    private fun deleteAddressError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
    }

    override fun onDismissDeleteAddressDialog() {
        _state.update { it.copy(isDeleteAddressVisible = false) }
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
                allAddresses = it.allAddresses.toMutableList().apply { add(it.addAddressState) }
            )
        }
        _state.update { it.copy(addAddressState = AddressUiState()) }
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
        val code = value.zipCode.validateRequireFields()
        val phone = value.phone.validateRequireFields()
        val hasError = listOf(country, name, streetAddress, code, city, phone).any { !it }
        _state.update {
            it.copy(
                addAddressState = it.addAddressState.copy(
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

    // region add credit
    override fun onChangeCreditNumber(number: String) {
        _state.update { it.copy(addCreditState = it.addCreditState.copy(cardNum = number)) }
    }

    override fun onChangeExpiration(number: String) {
        _state.update { it.copy(addCreditState = it.addCreditState.copy(expiration = number)) }
    }

    override fun onChangeSecurityNumber(number: String) {
        _state.update { it.copy(addCreditState = it.addCreditState.copy(securityCode = number)) }
    }

    override fun onChangeHolderNMame(name: String) {
        _state.update { it.copy(addCreditState = it.addCreditState.copy(holderName = name)) }
    }

    override fun addCard() {
        if (validateAddCredit()) {

        }
    }

    private fun validateAddCredit(): Boolean {
        val value = state.value.addCreditState
        val cardNum = value.cardNum.validateRequireFields()
        val expiration = value.expiration.validateRequireFields()
        val securityCode = value.securityCode.validateRequireFields()
        val holderName = value.holderName.validateRequireFields()

        val hasError = listOf(cardNum, expiration, securityCode, holderName).any { !it }
        _state.update {
            it.copy(
                addCreditState = it.addCreditState.copy(
                    cardNumError = !cardNum,
                    expirationError = !expiration,
                    securityCodeError = !securityCode,
                    holderNameError = !holderName
                )
            )
        }

        return !hasError
    }

    //endregion

    override fun onSwitchContent(content: CartCheckOutUiState.VisibleContent) {
        _state.update { it.copy(visibleContent = content) }
    }

    override fun onChoosePaymentMethodL(payment: PaymentMethod) {
        _state.update { it.copy(paymentMethod = payment) }
    }

    override fun checkOutOder() {

    }

    override fun controlAddCreditVisibility() {
        _state.update { it.copy(isAddCreditCardVisible = !it.isAddCreditCardVisible) }
    }

}
