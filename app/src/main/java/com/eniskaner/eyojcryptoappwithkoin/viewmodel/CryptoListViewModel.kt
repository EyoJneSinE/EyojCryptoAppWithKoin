package com.eniskaner.eyojcryptoappwithkoin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eniskaner.eyojcryptoappwithkoin.model.CryptoAllListItem
import com.eniskaner.eyojcryptoappwithkoin.repo.CryptoRepository
import com.eniskaner.eyojcryptoappwithkoin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    private val _cryptoList = MutableLiveData<List<CryptoAllListItem>>()
    val cryptoList: LiveData<List<CryptoAllListItem>> get() = _cryptoList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var initialCryptoList = listOf<CryptoAllListItem>()
    private var isSearchStarting = true

    init {
        loadCryptos()
    }

    fun searchCryptoList(query: String) {
        val listToSearch = if (isSearchStarting) {
            _cryptoList.value ?: emptyList()
        } else {
            initialCryptoList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                _cryptoList.postValue(initialCryptoList)
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.symbol.contains(query.trim(), ignoreCase = true)
            }
            if (isSearchStarting) {
                initialCryptoList = _cryptoList.value ?: emptyList()
                isSearchStarting = false
            }
            _cryptoList.postValue(results)
        }
    }

    fun loadCryptos() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = repository.getCyrptoList()
            when (result) {
                is Resource.Success -> {
                    val cryptoItems = result.data!!.mapIndexed { index, item ->
                        CryptoAllListItem(
                            item.beta_value, item.circulating_supply, item.first_data_at, item.id,
                            item.last_updated, item.max_supply, item.name, item.quotes, item.rank, item.symbol, item.total_supply
                        )
                    }
                    _cryptoList.postValue(cryptoItems)
                    _errorMessage.postValue("")
                    _isLoading.postValue(false)
                }
                is Resource.Error -> {
                    _errorMessage.postValue(result.message ?: "Error occurred!")
                    _isLoading.postValue(false)
                }
                is Resource.Loading -> {
                    _errorMessage.postValue("")
                }
            }
        }
    }
}

