package com.example.swiftbargain.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("FavoriteProductEntity")
data class FavoriteProductEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String,
    @ColumnInfo val rate: String,
    @ColumnInfo val price: String,
    @ColumnInfo val discountPercentage: String,
    @ColumnInfo val priceAfterDiscount: String,
    @ColumnInfo val imageUrl: String
)
