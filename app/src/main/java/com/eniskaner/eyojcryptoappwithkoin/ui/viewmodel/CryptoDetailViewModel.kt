package com.eniskaner.eyojcryptoappwithkoin.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCrypto
import com.eniskaner.eyojcryptoappwithkoin.data.usecase.CryptoDetailUseCase
import com.eniskaner.eyojcryptoappwithkoin.network.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.utils.Resource
import kotlinx.coroutines.flow.Flow

class CryptoDetailViewModel(private val detailUseCase: CryptoDetailUseCase): ViewModel() {

    suspend fun getCrypto(id: String) : Flow<Resource<Crypto>> {
        return detailUseCase.getCrypto(id)
    }

    fun getLocalCrypto(cryptoId: String): Flow<List<LocalCrypto>>  {
        return detailUseCase.getSavedCrypto(cryptoId)
    }
}