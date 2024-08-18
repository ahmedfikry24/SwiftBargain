package com.example.swiftbargain.navigation

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
object Search
