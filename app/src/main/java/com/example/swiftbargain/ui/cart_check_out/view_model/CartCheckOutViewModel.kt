package com.example.swiftbargain.ui.cart_check_out.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.swiftbargain.data.local.room.entity.CartProductEntity
import com.example.swiftbargain.data.local.room.entity.CreditEntity
import com.example.swiftbargain.data.models.AddressDto
import com.example.swiftbargain.data.repository.Repository
import com.example.swiftbargain.navigation.CartCheckOut
import com.example.swiftbargain.ui.base.BaseError
import com.example.swiftbargain.ui.base.BaseViewModel
import com.example.swiftbargain.ui.base.UserNotFound
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.shared_ui_state.AddressUiState
import com.example.swiftbargain.ui.utils.shared_ui_state.CreditUiSate
import com.example.swiftbargain.ui.utils.shared_ui_state.PaymentMethod
import com.example.swiftbargain.ui.utils.shared_ui_state.toDto
import com.example.swiftbargain.ui.utils.shared_ui_state.toEntity
import com.example.swiftbargain.ui.utils.shared_ui_state.toUiState
import com.example.swiftbargain.ui.utils.validateRequireFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
        getData()
    }

    override fun getData() {
        _state.update { it.copy(contentStatus = ContentStatus.LOADING) }
        tryExecute(
            {
                mapOf(
                    ADDRESSES to repository.getUserAddress(),
                    CREDIT_CARD to repository.getAllCreditCards(),
                    CART to repository.getAllCartProducts()
                )
            },
            ::dataSuccess,
            ::dataError
        )
    }

    private fun dataSuccess(success: Map<String, List<Any>>) {
        _state.update { value ->
            value.copy(
                contentStatus = ContentStatus.VISIBLE,
                allAddresses = (success[ADDRESSES] as List<*>).map { (it as AddressDto).toUiState() },
                allCreditCards = (success[CREDIT_CARD] as List<*>).map { (it as CreditEntity).toUiState() },
                allCartProducts = (success[CART] as List<*>).map { (it as CartProductEntity).toUiState() },
                currentOder = value.currentOder.copy(price = args.totalPrice)
            )
        }
    }

    private fun dataError(error: BaseError) {
        _state.update { it.copy(contentStatus = ContentStatus.FAILURE) }
        if (error is UserNotFound) sendEvent(CartCheckOutEvents.UnAuthorizedToAccess)
    }

    override fun onClickBack() {
        sendEvent(CartCheckOutEvents.NavigateToBack)
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
        if (error is UserNotFound) sendEvent(CartCheckOutEvents.UnAuthorizedToAccess)
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
        sendEvent(CartCheckOutEvents.AddAddressSuccess)
    }

    private fun addressError(error: BaseError) {
        _state.update { it.copy(addAddressState = it.addAddressState.copy(contentStatus = ContentStatus.FAILURE)) }
        if (error is UserNotFound) sendEvent(CartCheckOutEvents.UnAuthorizedToAccess)
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

    override fun onSwitchContent(content: CartCheckOutUiState.VisibleContent) {
        _state.update { it.copy(visibleContent = content) }
    }

    override fun onChoosePaymentMethod(payment: PaymentMethod) {
        _state.update { it.copy(paymentMethod = payment) }
    }

    override fun controlAddCreditVisibility() {
        _state.update { it.copy(isAddCreditCardVisible = !it.isAddCreditCardVisible) }
    }

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
            viewModelScope.launch {
                repository.addCreditCard(state.value.addCreditState.toEntity())
                _state.update {
                    it.copy(
                        isAddCreditCardVisible = false,
                        addCreditState = CreditUiSate(),
                        allCreditCards = it.allCreditCards.toMutableList()
                            .apply { add(it.addCreditState) }
                    )
                }
                sendEvent(CartCheckOutEvents.AddCreditSuccess)
            }
        }
    }

    private fun validateAddCredit(): Boolean {
        val value = state.value.addCreditState
        val cardNum = value.cardNum.validateRequireFields() && value.cardNum.length == 16
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


    override fun onSelectCredit(card: CreditUiSate) {
        _state.update { it.copy(selectedCreditCard = card) }
    }

    override fun checkOutOder() {
        _state.update { it.copy(chooseCardContentStatus = ContentStatus.LOADING) }
        val value = state.value
        tryExecute(
            {
                repository.addOrder(
                    value.currentOder.toDto().copy(
                        date = getTimeNow(),
                        numOfItems = value.allCartProducts.size.toString(),
                        productsId = value.allCartProducts.map { it.id },
                        address = value.selectedAddress.toDto()
                    )
                )
            },
            { orderSuccess() },
            ::orderError
        )
    }

    private fun orderSuccess() {
        viewModelScope.launch { sendEvent(CartCheckOutEvents.OrderSuccess) }
    }

    private fun orderError(error: BaseError) {
        _state.update { it.copy(chooseCardContentStatus = ContentStatus.FAILURE) }
        if (error is UserNotFound) sendEvent(CartCheckOutEvents.UnAuthorizedToAccess)
    }

    private fun getTimeNow(): String {
        val formatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDateTime.now().format(formatPattern)
    }

    companion object {
        private const val ADDRESSES = "addresses"
        private const val CREDIT_CARD = "creditCards"
        private const val CART = "cartProducts"
    }
}
