package com.eniskaner.eyojcryptoappwithkoin.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.eniskaner.eyojcryptoappwithkoin.data.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.network.repo.CryptoRepository
import com.eniskaner.eyojcryptoappwithkoin.utils.Resource

class CryptoDetailViewModel(private val repository: CryptoRepository): ViewModel() {
    suspend fun getCrypto(id: String) : Resource<Crypto> {
        return repository.getCrypto(id)
    }
}