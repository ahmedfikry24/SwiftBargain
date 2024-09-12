package com.example.swiftbargain.ui.utils.shred_interactions

interface AddAddressInteractions {
    fun onChangeCountry(country: String)
    fun onChangeName(name: String)
    fun onChangeStreetAddress(address: String)
    fun onChangeStreetAddress2(address: String)
    fun onChangeCity(city: String)
    fun onChangeZipCode(code: String)
    fun onChangePhone(phone: String)
    fun onClickAddAddress()
}