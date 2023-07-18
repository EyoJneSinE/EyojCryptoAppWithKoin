package com.eniskaner.eyojcryptoappwithkoin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eniskaner.eyojcryptoappwithkoin.model.CryptoAllListItem
import com.eniskaner.eyojcryptoappwithkoin.repo.CryptoRepository
import com.eniskaner.eyojcryptoappwithkoin.util.Resource
import com.eniskaner.eyojcryptoappwithkoin.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel(), CoroutineScope {
    private val _cryptoList = MutableLiveData<Resource<List<CryptoAllListItem>>>()
    val cryptoList: LiveData<Resource<List<CryptoAllListItem>>> get() = _cryptoList
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private var initialCryptoList = listOf<CryptoAllListItem>()
    private var isSearchStarting = true
    private val job = Job()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        _errorMessage.postValue(throwable.localizedMessage ?: "Error!")
        _isLoading.postValue(false)
    }
    override val coroutineContext: CoroutineContext get() = Dispatchers.IO + job + exceptionHandler
    init {
        loadCryptos()
    }
    fun searchCryptoList(query: String) {
        val listToSearch = if (isSearchStarting) {
            _cryptoList.value?.data ?: emptyList()
        } else {
            initialCryptoList
        }
        launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                _cryptoList.postValue(Resource.success(initialCryptoList))
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.symbol.contains(query.trim(), ignoreCase = true)
            }
            if (isSearchStarting) {
                initialCryptoList = _cryptoList.value?.data ?: emptyList()
                isSearchStarting = false
            }
            _cryptoList.postValue(Resource.success(results))
        }
    }
    fun loadCryptos() {
        _isLoading.postValue(true)
        _errorMessage.postValue("") // İlk başta hata mesajını temizleyin
        launch {
            val result = repository.getCyrptoList()
            when (result.status) {
                Status.SUCCESS -> {
                    val cryptoItems = result.data!!.mapIndexed { index, item ->
                        CryptoAllListItem(
                            item.beta_value, item.circulating_supply, item.first_data_at, item.id,
                            item.last_updated, item.max_supply, item.name, item.quotes, item.rank, item.symbol, item.total_supply
                        )
                    }
                    _cryptoList.postValue(Resource.success(cryptoItems))
                }
                Status.ERROR -> {
                    _errorMessage.postValue(result.message ?: "Error occurred!")
                    _cryptoList.postValue(Resource.error(result.message ?: "Error occurred!", null))
                }
                Status.LOADING -> {
                    _cryptoList.postValue(Resource.loading(null))
                }
            }
            _isLoading.postValue(false)
        }
    }
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}