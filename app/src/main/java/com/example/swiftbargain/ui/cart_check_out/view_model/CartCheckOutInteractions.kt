package com.example.swiftbargain.ui.cart_check_out.view_model

import com.example.swiftbargain.ui.utils.shred_interactions.AddAddressInteractions

interface CartCheckOutInteractions : AddAddressInteractions {
    fun onClickBack()
    fun controlAddAddressVisibility()
}
