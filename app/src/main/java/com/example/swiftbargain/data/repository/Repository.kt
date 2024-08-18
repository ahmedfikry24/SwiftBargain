package com.example.swiftbargain.data.repository

import android.content.Intent
import com.example.swiftbargain.data.models.CategoryDto
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.models.SaleAdDto
import kotlinx.coroutines.flow.Flow

interface Repository {
    // region data store
    suspend fun getUserUid(): Flow<String>
    suspend fun setUserUid(uid: String)
    // endregion

    // region auth
    suspend fun loginWithEmailAndPassword(email: String, password: String): String
    suspend fun signWithGoogleIntent(intent: Intent): String
    suspend fun loginWithGoogle(id: String): String
    suspend fun loginWithFacebook(id: String): String
    suspend fun resetPassword(email: String)
    suspend fun registerWithEmailAndPassword(name: String, email: String, password: String)
    //endregion

    // region user
    suspend fun getAllCategories(): List<CategoryDto>
    suspend fun getSaleAds(): List<SaleAdDto>
    suspend fun getAllProducts(): List<ProductDto>
    suspend fun getSaleProducts(saleId: String, lastItemId: String?): List<ProductDto>
    suspend fun searchSaleProducts(saleId: String, itemName: String): List<ProductDto>
    //endregion
}