package com.eniskaner.eyojcryptoappwithkoin.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCrypto
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCryptoList

@Dao
interface CryptoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(localCryptoList: LocalCryptoList)

    @Query("SELECT * FROM local_crypto_list")
    fun getLocalCryptoList(): List<LocalCryptoList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upCrypto(localCrypto: LocalCrypto)

    @Query("SELECT * FROM local_crypto WHERE cryptoId = :cryptoId")
    fun getLocalCrypto(cryptoId: String): LocalCrypto
}