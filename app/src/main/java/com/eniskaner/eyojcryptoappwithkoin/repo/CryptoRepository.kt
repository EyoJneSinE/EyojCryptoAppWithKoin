package com.eniskaner.eyojcryptoappwithkoin.repo

import com.eniskaner.eyojcryptoappwithkoin.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.model.CryptoAllList
import com.eniskaner.eyojcryptoappwithkoin.util.Resource

interface CryptoRepository {
    suspend fun getCryptoList(): Resource<CryptoAllList>
    suspend fun getCrypto(id: String): Resource<Crypto>
}