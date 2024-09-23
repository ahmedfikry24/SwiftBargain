package com.example.swiftbargain.ui.utils.shred_interactions

interface AddCreditInteractions {
    fun onChangeCreditNumber(number: String)
    fun onChangeExpiration(number: String)
    fun onChangeSecurityNumber(number: String)
    fun onChangeHolderNMame(name: String)
    fun addCard()
}