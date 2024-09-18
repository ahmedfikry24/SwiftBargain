package com.example.swiftbargain.data.repository

import android.content.Intent
import com.example.swiftbargain.data.local.room.entity.CartProductEntity
import com.example.swiftbargain.data.local.room.entity.CreditEntity
import com.example.swiftbargain.data.local.room.entity.FavoriteProductEntity
import com.example.swiftbargain.data.models.AddressDto
import com.example.swiftbargain.data.models.CategoryDto
import com.example.swiftbargain.data.models.CouponCodeDto
import com.example.swiftbargain.data.models.OrderDto
import com.example.swiftbargain.data.models.ProductDto
import com.example.swiftbargain.data.models.ReviewDto
import com.example.swiftbargain.data.models.SaleAdDto
import com.example.swiftbargain.data.models.UserInfoDto
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
    suspend fun getProductDetails(id: String): ProductDto
    suspend fun getProductReviews(id: String): List<ReviewDto>
    suspend fun addFavoriteProduct(product: FavoriteProductEntity)
    suspend fun getAllFavorites(): List<FavoriteProductEntity>
    suspend fun removeFavoriteProduct(id: String)
    suspend fun addProductToCart(product: CartProductEntity)
    suspend fun getAllCartProducts(): List<CartProductEntity>
    suspend fun removeProductFromCart(id: String)
    suspend fun deleteAllCartProducts()
    suspend fun addProductReview(uid: String, productId: String, review: ReviewDto): ReviewDto
    suspend fun searchProducts(itemName: String): List<ProductDto>
    suspend fun getCategoryProducts(categoryId: String, lastItemId: String?): List<ProductDto>
    suspend fun getAllCouponCodes(): List<CouponCodeDto>
    suspend fun addUserAddressInfo(address: AddressDto)
    suspend fun getUserAddress(): List<AddressDto>
    suspend fun deleteUserAddress(address: AddressDto)
    suspend fun addCreditCard(card: CreditEntity)
    suspend fun getAllCreditCards(): List<CreditEntity>
    suspend fun deleteAllCreditCards()
    suspend fun addOrder(order: OrderDto)
    suspend fun getAllOrders(): List<OrderDto>
    suspend fun getUserInfo(): UserInfoDto
    suspend fun updateProfileInfo(info: UserInfoDto, password: String?)
    //endregion
}