package com.eniskaner.eyojcryptoappwithkoin.network.repo

import com.eniskaner.eyojcryptoappwithkoin.network.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.network.model.CryptoAllList
import com.eniskaner.eyojcryptoappwithkoin.network.service.CryptoAPI
import com.eniskaner.eyojcryptoappwithkoin.utils.Resource

class CryptoRepositoryImplementation(private val api: CryptoAPI) : CryptoRepository {

    override suspend fun getCryptoList(): Resource<CryptoAllList> {
        return try {
            val response = api.getCyrptoList()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Resource.success(data)
                } else {
                    Resource.error("Error", null)
                }
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("Error", null)
        }
    }

    override suspend fun getCrypto(id: String): Resource<Crypto> {
        return try {
            val response = api.getCyrpto(id)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Resource.success(data)
                } else {
                    Resource.error("Error", null)
                }
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("Error", null)
        }
    }
}