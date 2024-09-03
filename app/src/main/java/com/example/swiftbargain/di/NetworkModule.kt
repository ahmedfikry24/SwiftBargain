package com.example.swiftbargain.di

import android.content.Context
import com.example.swiftbargain.data.utils.InternetConnectivityChecker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideFireBasFireStore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideFireBaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideInternetConnectivityChecker(@ApplicationContext context: Context): InternetConnectivityChecker =
        InternetConnectivityChecker(context)
}