package com.eniskaner.eyojcryptoappwithkoin.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "local_crypto_list")
@Parcelize
data class LocalCryptoList(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var cryptoId: String,
    var price:  Double,
    var symbol: String
): Parcelable