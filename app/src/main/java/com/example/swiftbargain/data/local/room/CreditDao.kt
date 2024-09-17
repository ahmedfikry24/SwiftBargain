package com.example.swiftbargain.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.swiftbargain.data.local.room.entity.CreditEntity

@Dao
interface CreditDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(card: CreditEntity)

    @Query("select * from CreditEntity")
    suspend fun getAllCards(): List<CreditEntity>

    @Query("delete from CreditEntity")
    suspend fun clearCards()
}