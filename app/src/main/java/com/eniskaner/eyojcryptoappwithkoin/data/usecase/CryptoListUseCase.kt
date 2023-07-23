package com.eniskaner.eyojcryptoappwithkoin.data.usecase

import com.eniskaner.eyojcryptoappwithkoin.data.local.CryptoDao
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCryptoList
import com.eniskaner.eyojcryptoappwithkoin.network.model.CryptoAllListItem
import com.eniskaner.eyojcryptoappwithkoin.network.repo.CryptoRepository
import com.eniskaner.eyojcryptoappwithkoin.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOError

class CryptoListUseCase(
    private val repository: CryptoRepository,
    private val localDataSource: CryptoDao
) {
    fun getCryptoList(): Flow<Resource<List<CryptoAllListItem>>> = flow {
        try {
            val response = repository.getCryptoList()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Could not reach internet"))
        }
    }.flowOn(Dispatchers.IO)
    fun saveCryptoList(localCryptoList: LocalCryptoList) = flow  {
        try {
            localDataSource.upsert(localCryptoList)
            emit(Resource.Success(localCryptoList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error!", null))
        }
    }.flowOn(Dispatchers.IO)
    fun getSavedCryptoList(): Flow<Resource<List<CryptoAllListItem>>> = flow<Resource<List<CryptoAllListItem>>> {
        localDataSource.getLocalCryptoList()
    }.flowOn(Dispatchers.IO)
}