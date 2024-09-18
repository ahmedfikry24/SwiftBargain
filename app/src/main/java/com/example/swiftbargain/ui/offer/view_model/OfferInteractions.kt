package com.example.swiftbargain.ui.offer.view_model

interface OfferInteractions {
    fun getSaleAds()
    fun onClickAd(id: String, title: String, url: String)
}
