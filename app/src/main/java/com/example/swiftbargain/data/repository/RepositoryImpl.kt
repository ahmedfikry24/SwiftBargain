package com.example.swiftbargain.data.repository

import android.content.Intent
import com.example.swiftbargain.data.local.DataStoreManager
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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataStore: DataStoreManager,
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val connectivityChecker: InternetConnectivityChecker
) : Repository {

    override suspend fun getIsLogin() = dataStore.isLogin

    override suspend fun setIsLogin(value: Boolean) = dataStore.setIsLogin(value)

    override suspend fun getUserUid(): Flow<String> = dataStore.userUid

    override suspend fun setUserUid(uid: String) = dataStore.setUserUid(uid)

    override suspend fun loginWithEmailAndPassword(email: String, password: String): String {
        return wrapApiCall(connectivityChecker) {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw UserNotFound()
            val info = fireStore.collection("users").document(user.uid).get().await()
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
                email = user.email
            )
            fireStore.collection("users").document(user.uid).set(userInfo).await()
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
                email = user.email
            )
            fireStore.collection("users").document(user.uid).set(userInfo).await()
            user.uid
        }
    }

    override suspend fun resetPassword(email: String) {
        return wrapApiCall(connectivityChecker) {
            auth.sendPasswordResetEmail(email).await()
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
                email = email
            )
            fireStore.collection("users").document(user.uid).set(userInfo).await()
            user.sendEmailVerification().await()
        }
    }
}