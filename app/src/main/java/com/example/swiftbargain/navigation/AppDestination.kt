package com.example.swiftbargain.navigation

import android.os.Parcelable
import com.example.swiftbargain.ui.utils.shared_ui_state.ReviewUiState
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
object Authentication

@Serializable
object Login

@Serializable
object Register

@Serializable
object User

@Serializable
object Home

@Serializable
object Explore

@Serializable
object Cart

@Serializable
object Offer

@Serializable
object Account

@Serializable
data class Sale(val id: String, val title: String, val url: String)

@Serializable
data class Category(val id: String)

@Serializable
data class ProductDetails(val id: String)

@Serializable
@Parcelize
data class ReviewsParam(val items: List<ReviewUiState>) : Parcelable

@Serializable
data class ProductReviews(val param: ReviewsParam)

@Serializable
object Search
