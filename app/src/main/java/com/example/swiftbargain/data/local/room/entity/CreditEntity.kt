package com.example.swiftbargain.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("CreditEntity")
data class CreditEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val cardNumber: String,
    @ColumnInfo val expiration: String,
    @ColumnInfo val securityCode: String,
    @ColumnInfo val holderName: String,
)
