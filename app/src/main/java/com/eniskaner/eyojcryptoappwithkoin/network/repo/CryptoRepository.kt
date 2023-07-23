package com.eniskaner.eyojcryptoappwithkoin.network.repo

import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCrypto
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCryptoList
import com.eniskaner.eyojcryptoappwithkoin.network.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.network.model.CryptoAllListItem

interface CryptoRepository {
    suspend fun getCryptoList(): List<CryptoAllListItem>
    suspend fun getCrypto(id: String): Crypto

    suspend fun saveCryptoList(localCryptoList: LocalCryptoList)

    fun getSavedCryptoList(): List<LocalCryptoList>

    suspend fun savedCrypto(localCrypto: LocalCrypto)

    fun getSavedCrypto(cryptoId: String): LocalCrypto
}