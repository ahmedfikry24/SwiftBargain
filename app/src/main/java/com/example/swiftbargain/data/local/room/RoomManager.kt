package com.example.swiftbargain.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.swiftbargain.data.local.room.entity.CartProductEntity
import com.example.swiftbargain.data.local.room.entity.FavoriteProductEntity

@Database(entities = [FavoriteProductEntity::class, CartProductEntity::class], version = 1)
abstract class RoomManager : RoomDatabase() {

    abstract val favorites: FavoritesDao
    abstract val cart: CartDao

    companion object {
        @Volatile
        private var instance: RoomManager? = null

        fun getInit(context: Context): RoomManager {
            return instance ?: synchronized(context) {
                return instance ?: Room
                    .databaseBuilder(
                        context.applicationContext,
                        RoomManager::class.java,
                        "infoDataBase"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
