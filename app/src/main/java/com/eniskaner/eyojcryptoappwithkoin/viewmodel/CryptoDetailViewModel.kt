package com.eniskaner.eyojcryptoappwithkoin.viewmodel

import androidx.lifecycle.ViewModel
import com.eniskaner.eyojcryptoappwithkoin.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.repo.CryptoRepository
import com.eniskaner.eyojcryptoappwithkoin.util.Resource

class CryptoDetailViewModel(private val repository: CryptoRepository): ViewModel() {

    suspend fun getCrypto(id: String) : Resource<Crypto> {
        return repository.getCrypto(id)
    }
}