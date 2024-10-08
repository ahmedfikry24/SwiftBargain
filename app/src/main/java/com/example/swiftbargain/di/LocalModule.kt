package com.example.swiftbargain.di

import android.content.Context
import com.example.swiftbargain.data.local.DataStoreManager
import com.example.swiftbargain.data.local.room.RoomManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context) =
        DataStoreManager(context)

    @Provides
    @Singleton
    fun provideRoomManager(@ApplicationContext context: Context) = RoomManager.getInit(context)
}