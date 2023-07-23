package com.eniskaner.eyojcryptoappwithkoin.network.repo

import com.eniskaner.eyojcryptoappwithkoin.data.local.CryptoDao
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCrypto
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCryptoList
import com.eniskaner.eyojcryptoappwithkoin.network.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.network.model.CryptoAllListItem
import com.eniskaner.eyojcryptoappwithkoin.network.service.CryptoAPI

class CryptoRepositoryImplementation(
    private val api: CryptoAPI,
    private val localDataSource: CryptoDao
) : CryptoRepository {
    override suspend fun getCryptoList(): List<CryptoAllListItem> {
        return api.getCyrptoList()
    }
    override suspend fun getCrypto(id: String): Crypto  {
        return api.getCyrpto(id)
    }
    override suspend fun saveCryptoList(localCryptoList: LocalCryptoList) {
        return localDataSource.upsert(localCryptoList)
    }
    override fun getSavedCryptoList(): List<LocalCryptoList> {
        return localDataSource.getLocalCryptoList()
    }
    override suspend fun savedCrypto(localCrypto: LocalCrypto) {
        return localDataSource.upCrypto(localCrypto)
    }
    override fun getSavedCrypto(cryptoId: String): LocalCrypto {
        return localDataSource.getLocalCrypto(cryptoId)
    }
}