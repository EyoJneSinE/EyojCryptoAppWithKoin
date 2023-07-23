package com.eniskaner.eyojcryptoappwithkoin.data.usecase

import com.eniskaner.eyojcryptoappwithkoin.data.local.CryptoDao
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCrypto
import com.eniskaner.eyojcryptoappwithkoin.network.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.network.repo.CryptoRepository
import com.eniskaner.eyojcryptoappwithkoin.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOError

class CryptoDetailUseCase(
    private val repository: CryptoRepository,
    private val localDataSource: CryptoDao
) {
    fun getCrypto(id: String): Flow<Resource<Crypto>> = flow {
        try {
            val response = repository.getCrypto(id)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Could not reach internet"))
        }
    }.flowOn(Dispatchers.IO)
    fun savedCrypto(localCrypto: LocalCrypto) = flow {
        try {
            localDataSource.upCrypto(localCrypto)
            emit(Resource.Success(localCrypto))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error!", null))
        }
    }.flowOn(Dispatchers.IO)
    fun getSavedCrypto(cryptoId: String): Flow<List<LocalCrypto>> = flow<List<LocalCrypto>> {
        localDataSource.getLocalCrypto(cryptoId)
    }.flowOn(Dispatchers.IO)
}