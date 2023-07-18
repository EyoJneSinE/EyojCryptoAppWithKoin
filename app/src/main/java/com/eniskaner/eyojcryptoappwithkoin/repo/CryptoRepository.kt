package com.eniskaner.eyojcryptoappwithkoin.repo

import com.eniskaner.eyojcryptoappwithkoin.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.model.CryptoAllList
import com.eniskaner.eyojcryptoappwithkoin.service.CryptoAPI
import com.eniskaner.eyojcryptoappwithkoin.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api: CryptoAPI
) {
    suspend fun getCyrptoList() : Resource<CryptoAllList> {
        val response = try {
            api.getCyrptoList()
        } catch (e: Exception) {
            return Resource.error("Error", null)
        }
        return Resource.success(response)
    }

    suspend fun getCyrpto(id: String) : Resource<Crypto> {
        val response = try {
            api.getCyrpto(id)
        } catch (e: Exception) {
            return Resource.error("Error", null)
        }
        return Resource.success(response)
    }
}