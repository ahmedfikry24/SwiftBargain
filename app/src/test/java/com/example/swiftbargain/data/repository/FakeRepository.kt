package com.example.swiftbargain.data.repository

import android.net.Uri
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
import kotlinx.coroutines.flow.flow

class FakeRepository : Repository {

    private var userUid = "ahmed123"
    private val products = mutableListOf<ProductDto>()
    private val categories = mutableListOf<CategoryDto>()
    private val saleAds = mutableListOf<SaleAdDto>()
    private val reviews = mutableListOf<ReviewDto>()
    private val favoriteProduct = mutableSetOf<FavoriteProductEntity>()
    private val cartProduct = mutableListOf<CartProductEntity>()
    private val coupons = mutableListOf<CouponCodeDto>()
    private val addresses = mutableListOf<AddressDto>()
    private val creditCards = mutableListOf<CreditEntity>()
    private val orders = mutableListOf<OrderDto>()
    private var userInfo = UserInfoDto(
        id = userUid,
        name = "ahmed",
        email = "ahmed@gmail.com",
        imageUrl = "url",
        gender = "male",
        birthday = "2019-10-20",
        phone = "'0101012030",
        addresses = listOf()
    )

    override suspend fun getUserUid() = flow { emit(userUid) }

    override suspend fun setUserUid(uid: String) {
        userUid = uid
    }

    override suspend fun loginWithEmailAndPassword(email: String, password: String): String {
        return userUid
    }

    override suspend fun loginWithGoogle(id: String): String {
        return userUid
    }

    override suspend fun loginWithFacebook(id: String): String {
        return userUid
    }

    override suspend fun resetPassword(email: String) {

    }

    override suspend fun registerWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ) {

    }

    override suspend fun getAllCategories(): List<CategoryDto> {
        return categories
    }

    override suspend fun getSaleAds(): List<SaleAdDto> {
        return saleAds
    }

    override suspend fun getAllProducts(): List<ProductDto> {
        return products
    }

    override suspend fun getSaleProducts(saleId: String, lastItemId: String?): List<ProductDto> {
        return products.filter { it.saleId == saleId }
    }

    override suspend fun searchSaleProducts(saleId: String, itemName: String): List<ProductDto> {
        return products.filter { it.saleId == saleId && it.title.contains(itemName) }
    }

    override suspend fun getProductDetails(id: String): ProductDto {
        return products.first { it.id == id }
    }

    override suspend fun getProductReviews(id: String): List<ReviewDto> {
        return reviews
    }

    override suspend fun addFavoriteProduct(product: FavoriteProductEntity) {
        favoriteProduct.add(product)
    }

    override suspend fun getAllFavorites(): List<FavoriteProductEntity> {
        return favoriteProduct.toList()
    }

    override suspend fun removeFavoriteProduct(id: String) {
        favoriteProduct.remove(favoriteProduct.find { it.id == id })
    }

    override suspend fun addProductToCart(product: CartProductEntity) {
        cartProduct.add(product)
    }

    override suspend fun getAllCartProducts(): List<CartProductEntity> {
        return cartProduct
    }

    override suspend fun removeProductFromCart(id: String) {
        cartProduct.remove(cartProduct.find { it.id == id })
    }

    override suspend fun deleteAllCartProducts() {
        cartProduct.clear()
    }

    override suspend fun addProductReview(
        uid: String,
        productId: String,
        review: ReviewDto
    ): ReviewDto {
        reviews.add(review)
        return review
    }

    override suspend fun searchProducts(itemName: String): List<ProductDto> {
        return products.filter { it.title.contains(itemName) }
    }

    override suspend fun getCategoryProducts(
        categoryId: String,
        lastItemId: String?
    ): List<ProductDto> {
        return products.filter { it.categoryId == categoryId }
    }

    override suspend fun getAllCouponCodes(): List<CouponCodeDto> {
        return coupons
    }

    override suspend fun addUserAddressInfo(address: AddressDto) {
        addresses.add(address)
    }

    override suspend fun getUserAddress(): List<AddressDto> {
        return addresses
    }

    override suspend fun deleteUserAddress(address: AddressDto) {
        addresses.remove(address)
    }

    override suspend fun addCreditCard(card: CreditEntity) {
        creditCards.add(card)
    }

    override suspend fun getAllCreditCards(): List<CreditEntity> {
        return creditCards
    }

    override suspend fun deleteAllCreditCards() {
        creditCards.clear()
    }

    override suspend fun addOrder(order: OrderDto) {
        orders.add(order)
    }

    override suspend fun getAllOrders(): List<OrderDto> {
        return orders
    }

    override suspend fun getOrderDetails(id: String): Pair<OrderDto, List<ProductDto>> {
        val order = orders.first { it.id == id }
        val products = products.filter { product -> order.productsId.any { it == product.id } }
        return Pair(order, products)
    }

    override suspend fun getUserInfo(): UserInfoDto {
        return userInfo
    }

    override suspend fun updateProfileInfo(info: UserInfoDto, image: Uri?): String {
        userInfo = info
        return userInfo.imageUrl ?: ""
    }
}
