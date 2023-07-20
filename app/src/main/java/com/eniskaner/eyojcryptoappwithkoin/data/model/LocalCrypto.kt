package com.eniskaner.eyojcryptoappwithkoin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "local_cyrpto")
data class LocalCrypto (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val description: String,
    @SerializedName("id")
    val currencyId: String,
    val logo: String,
    val name: String,
    val symbol: String,
        ): Serializable