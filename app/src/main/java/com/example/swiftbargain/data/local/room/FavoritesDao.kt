package com.example.swiftbargain.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.swiftbargain.data.local.room.entity.FavoriteProductEntity

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: FavoriteProductEntity)

    @Query("select * from FavoriteProductEntity")
    suspend fun getAllProducts(): List<FavoriteProductEntity>

    @Query("delete from FAVORITEPRODUCTENTITY where id = :id")
    suspend fun deleteProduct(id: String)
}