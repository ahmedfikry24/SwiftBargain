package com.example.swiftbargain.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.swiftbargain.data.local.room.entity.CartProductEntity

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: CartProductEntity)

    @Query("select * from CartProductEntity")
    suspend fun getAllProducts(): List<CartProductEntity>

    @Query("delete from CartProductEntity where id = :id")
    suspend fun deleteProduct(id: String)
}