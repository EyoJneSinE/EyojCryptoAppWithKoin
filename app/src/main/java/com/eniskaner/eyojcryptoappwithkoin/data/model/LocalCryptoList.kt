package com.eniskaner.eyojcryptoappwithkoin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "local_cyrptolist")
data class LocalCryptoList(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("id")
    var cryptoId: String,
    var quotes: QQuotes
    )
