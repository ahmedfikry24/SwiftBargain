package com.example.swiftbargain.ui.utils.shred_interactions

interface AddAddressInteractions {
    fun onChangeCountry(country: String)
    fun onChangeFirstName(name: String)
    fun onChangeLastName(name: String)
    fun onChangeStreetAddress(address: String)
    fun onChangeStreetAddress2(address: String)
    fun onChangeCity(city: String)
    fun onChangeRegion(region: String)
    fun onChangeZipCode(code: String)
    fun onChangePhone(phone: String)
    fun onClickAddAddress()
}