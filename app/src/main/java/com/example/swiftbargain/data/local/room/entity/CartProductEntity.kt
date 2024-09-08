package com.example.swiftbargain.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("CartProductEntity")
data class CartProductEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String,
    @ColumnInfo val price: String,
    @ColumnInfo val color: String,
    @ColumnInfo val size: String,
    @ColumnInfo val imageUrl: String,
    @ColumnInfo val quantity: String,
)
