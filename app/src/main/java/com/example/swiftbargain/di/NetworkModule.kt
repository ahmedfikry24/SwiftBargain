package com.example.swiftbargain.di

import android.content.Context
import com.example.swiftbargain.utils.InternetConnectivityChecker
import com.google.firebase.auth.FirebaseAuth
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
    fun provideFireBaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideInternetConnectivityChecker(@ApplicationContext context: Context): InternetConnectivityChecker =
        InternetConnectivityChecker(context)
}