package com.example.swiftbargain.data.repository

import android.content.Intent
import com.example.swiftbargain.data.local.DataStoreManager
import com.example.swiftbargain.data.local.room.RoomManager
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
import com.example.swiftbargain.data.utils.InternetConnectivityChecker
import com.example.swiftbargain.data.utils.wrapApiCall
import com.example.swiftbargain.ui.base.EmailIsNoVerified
import com.example.swiftbargain.ui.base.RegistrationFailed
import com.example.swiftbargain.ui.base.UserNotFound
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataStore: DataStoreManager,
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val localDB: RoomManager,
    private val connectivityChecker: InternetConnectivityChecker
) : Repository {

    override suspend fun getUserUid(): Flow<String> = dataStore.userUid

    override suspend fun setUserUid(uid: String) = dataStore.setUserUid(uid)

    override suspend fun loginWithEmailAndPassword(email: String, password: String): String {
        return wrapApiCall(connectivityChecker) {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw UserNotFound()
            val info = fireStore.collection(USERS).document(user.uid).get().await()
            info.data ?: throw UserNotFound()
            if (!user.isEmailVerified)
                throw EmailIsNoVerified()
            user.uid
        }
    }

    override suspend fun signWithGoogleIntent(intent: Intent): String {
        return wrapApiCall(connectivityChecker) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(intent).await()
            account.idToken ?: throw UserNotFound()
        }
    }

    override suspend fun loginWithGoogle(id: String): String {
        return wrapApiCall(connectivityChecker) {
            val credential = GoogleAuthProvider.getCredential(id, null)
            val result = auth.signInWithCredential(credential).await()
            val user = result.user ?: throw UserNotFound()
            val userInfo = UserInfoDto(
                id = user.uid,
                name = user.displayName,
                email = user.email,
                imageUrl = user.photoUrl.toString(),
                phone = user.phoneNumber ?: ""
            )
            fireStore.collection(USERS).document(user.uid).set(userInfo).await()
            user.uid
        }
    }

    override suspend fun loginWithFacebook(id: String): String {
        return wrapApiCall(connectivityChecker) {
            val credential = FacebookAuthProvider.getCredential(id)
            val result = auth.signInWithCredential(credential).await()
            val user = result.user ?: throw UserNotFound()
            val userInfo = UserInfoDto(
                id = user.uid,
                name = user.displayName,
                email = user.email,
                imageUrl = user.photoUrl.toString(),
                phone = user.phoneNumber ?: ""
            )
            fireStore.collection(USERS).document(user.uid).set(userInfo).await()
            user.uid
        }
    }

    override suspend fun resetPassword(email: String) {
        return wrapApiCall(connectivityChecker) {
            val documents = fireStore.collection(USERS).get().await()
            val isFound = documents.documents.any { document ->
                document.data?.let { data -> data["email"] == email } == true
            }
            if (isFound) auth.sendPasswordResetEmail(email).await() else throw UserNotFound()
        }
    }

    override suspend fun registerWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ) {
        return wrapApiCall(connectivityChecker) {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw RegistrationFailed()
            val userInfo = UserInfoDto(
                id = user.uid,
                name = name,
                email = email,
                imageUrl = user.photoUrl.toString(),
                phone = user.phoneNumber ?: ""
            )
            fireStore.collection(USERS).document(user.uid).set(userInfo).await()
            user.sendEmailVerification().await()
        }
    }

    override suspend fun getAllCategories(): List<CategoryDto> {
        return wrapApiCall(connectivityChecker) {
            val result = fireStore.collection(CATEGORIES).get().await()
            result.toObjects(CategoryDto::class.java).shuffled()
        }
    }

    override suspend fun getSaleAds(): List<SaleAdDto> {
        return wrapApiCall(connectivityChecker) {
            val result = fireStore.collection(SALE_AD).get().await()
            result.toObjects(SaleAdDto::class.java)
        }
    }

    override suspend fun getAllProducts(): List<ProductDto> {
        return wrapApiCall(connectivityChecker) {
            val result = fireStore.collection(PRODUCTS).get().await()
            result.toObjects(ProductDto::class.java)
        }
    }

    override suspend fun getSaleProducts(saleId: String, lastItemId: String?): List<ProductDto> {
        return wrapApiCall(connectivityChecker) {
            var result = fireStore.collection(PRODUCTS)
                .whereEqualTo(SALE_ID, saleId)
                .orderBy(ID)

            if (lastItemId != null) {
                result = result.startAfter(lastItemId)
            }

            result.limit(10L).get().await().toObjects(ProductDto::class.java)
        }
    }

    override suspend fun searchSaleProducts(saleId: String, itemName: String): List<ProductDto> {
        return wrapApiCall(connectivityChecker) {
            val result = fireStore.collection(PRODUCTS)
                .whereEqualTo(SALE_ID, saleId)
                .orderBy(TITLE)
                .startAt(itemName)
                .endAt(itemName + "\uf8ff")
                .get().await()
            result.toObjects(ProductDto::class.java)
        }
    }

    override suspend fun getProductDetails(id: String): ProductDto {
        return wrapApiCall(connectivityChecker) {
            val result = fireStore.collection(PRODUCTS).document(id).get().await()
            result.toObject(ProductDto::class.java) ?: ProductDto()
        }
    }

    override suspend fun getProductReviews(id: String): List<ReviewDto> {
        return wrapApiCall(connectivityChecker) {
            val result =
                fireStore.collection(PRODUCTS).document(id).collection(REVIEWS).get().await()
            result.toObjects(ReviewDto::class.java)
        }
    }

    override suspend fun addFavoriteProduct(product: FavoriteProductEntity) {
        wrapApiCall(connectivityChecker) {
            localDB.favorites.addProduct(product)
        }
    }

    override suspend fun getAllFavorites(): List<FavoriteProductEntity> {
        return wrapApiCall(connectivityChecker) {
            localDB.favorites.getAllProducts()
        }
    }

    override suspend fun removeFavoriteProduct(id: String) {
        wrapApiCall(connectivityChecker) {
            localDB.favorites.deleteProduct(id)
        }
    }

    override suspend fun addProductToCart(product: CartProductEntity) {
        wrapApiCall(connectivityChecker) {
            localDB.cart.addProduct(product)
        }
    }

    override suspend fun getAllCartProducts(): List<CartProductEntity> {
        return wrapApiCall(connectivityChecker) {
            val localProducts = localDB.cart.getAllProducts().toMutableList()
            localProducts.forEachIndexed { index, product ->
                val remoteProduct = fireStore.collection(PRODUCTS)
                    .whereEqualTo(ID, product.id).get().await()
                    .toObjects(ProductDto::class.java).first()

                localProducts.apply {
                    this[index] = this[index].copy(quantity = remoteProduct.quantity)
                }
            }
            localProducts
        }
    }

    override suspend fun removeProductFromCart(id: String) {
        wrapApiCall(connectivityChecker) {
            localDB.cart.deleteProduct(id)
        }
    }

    override suspend fun deleteAllCartProducts() {
        localDB.cart.clearAllProducts()
    }

    override suspend fun addProductReview(
        uid: String,
        productId: String,
        review: ReviewDto
    ): ReviewDto {
        return wrapApiCall(connectivityChecker) {
            val result = fireStore.collection(USERS).document(uid).get().await()
            val user = result.toObject(UserInfoDto::class.java)
            val updatedReviewInfo = review.copy(name = user?.name ?: "", email = user?.email ?: "")
            fireStore.collection(PRODUCTS)
                .document(productId).collection(REVIEWS)
                .add(updatedReviewInfo)
                .await()
            updatedReviewInfo
        }
    }

    override suspend fun searchProducts(itemName: String): List<ProductDto> {
        return wrapApiCall(connectivityChecker) {
            val result = fireStore.collection(PRODUCTS)
                .orderBy(TITLE)
                .startAt(itemName)
                .endAt(itemName + "\uf8ff")
                .get().await()
            result.toObjects(ProductDto::class.java)
        }
    }

    override suspend fun getCategoryProducts(
        categoryId: String,
        lastItemId: String?
    ): List<ProductDto> {
        return wrapApiCall(connectivityChecker) {
            var result = fireStore.collection(PRODUCTS)
                .whereEqualTo(CATEGORY_ID, categoryId)
                .orderBy(ID)

            if (lastItemId != null) {
                result = result.startAfter(lastItemId)
            }
            result.limit(10L).get().await().toObjects(ProductDto::class.java)
        }
    }

    override suspend fun getAllCouponCodes(): List<CouponCodeDto> {
        return wrapApiCall(connectivityChecker) {
            val result = fireStore.collection(COUPONS).get().await()
            result.toObjects(CouponCodeDto::class.java)
        }
    }

    override suspend fun addUserAddressInfo(address: AddressDto) {
        wrapApiCall(connectivityChecker) {
            val currentUserId = auth.currentUser?.uid ?: throw UserNotFound()
            fireStore.collection(USERS)
                .document(currentUserId)
                .update(ADDRESSES, FieldValue.arrayUnion(address))
                .await()
        }
    }

    override suspend fun getUserAddress(): List<AddressDto> {
        return wrapApiCall(connectivityChecker) {
            val currentUserId = auth.currentUser?.uid ?: throw UserNotFound()
            val result = fireStore.collection(USERS).document(currentUserId).get().await()
            result.toObject(UserInfoDto::class.java)?.addresses ?: listOf()
        }
    }

    override suspend fun deleteUserAddress(address: AddressDto) {
        wrapApiCall(connectivityChecker) {
            val currentUserId = auth.currentUser?.uid ?: throw UserNotFound()
            fireStore.collection(USERS)
                .document(currentUserId)
                .update(ADDRESSES, FieldValue.arrayRemove(address))
                .await()
        }
    }

    override suspend fun addCreditCard(card: CreditEntity) {
        localDB.credit.addCard(card)
    }

    override suspend fun getAllCreditCards(): List<CreditEntity> {
        return localDB.credit.getAllCards()
    }

    override suspend fun deleteAllCreditCards() {
        localDB.credit.clearCards()
    }

    override suspend fun addOrder(order: OrderDto) {
        wrapApiCall(connectivityChecker) {
            val currentUserId = auth.currentUser?.uid ?: throw UserNotFound()
            val document =
                fireStore.collection(USERS).document(currentUserId).collection(ORDERS).document()
            document.set(order.copy(id = document.id)).await()
        }
    }

    override suspend fun getAllOrders(): List<OrderDto> {
        return wrapApiCall(connectivityChecker) {
            val currentUserId = auth.currentUser?.uid ?: throw UserNotFound()
            val result =
                fireStore.collection(USERS).document(currentUserId).collection(ORDERS).get().await()
            result.toObjects(OrderDto::class.java)
        }
    }

    companion object {
        private const val USERS = "users"
        private const val CATEGORIES = "categories"
        private const val SALE_AD = "sale_ad"
        private const val PRODUCTS = "products"
        private const val REVIEWS = "reviews"
        private const val COUPONS = "coupons"
        private const val ORDERS = "orders"

        private const val SALE_ID = "sale_id"
        private const val ID = "id"
        private const val TITLE = "title"
        private const val CATEGORY_ID = "category_id"
        private const val ADDRESSES = "addresses"
    }
}