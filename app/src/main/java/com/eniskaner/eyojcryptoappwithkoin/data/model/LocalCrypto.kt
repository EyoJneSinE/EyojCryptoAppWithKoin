package com.eniskaner.eyojcryptoappwithkoin.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "local_crypto")
@Parcelize
data class LocalCrypto(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val description: String? = null,
    val cryptoId: String,
    val logo: String,
    val name: String,
    val symbol: String,
) : Parcelable