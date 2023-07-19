package com.eniskaner.eyojcryptoappwithkoin.network.repo

import com.eniskaner.eyojcryptoappwithkoin.data.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.data.model.CryptoAllList
import com.eniskaner.eyojcryptoappwithkoin.utils.Resource

interface CryptoRepository {
    suspend fun getCryptoList(): Resource<CryptoAllList>
    suspend fun getCrypto(id: String): Resource<Crypto>
}