package com.eniskaner.eyojcryptoappwithkoin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCryptoList
import com.eniskaner.eyojcryptoappwithkoin.data.usecase.CryptoListUseCase
import com.eniskaner.eyojcryptoappwithkoin.network.model.CryptoAllListItem
import com.eniskaner.eyojcryptoappwithkoin.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CryptoListViewModel(private val cryptoListUseCase: CryptoListUseCase) : ViewModel() {
    private val _cryptoList = MutableStateFlow<Resource<List<CryptoAllListItem>>>(Resource.Loading())
    val cryptoList: StateFlow<Resource<List<CryptoAllListItem>>> = _cryptoList

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var initialCryptoList: List<CryptoAllListItem>? = null
    private var isSearchStarting = true

    init {
        loadCryptos()
    }
    fun searchCryptoList(query: String) {
        val listToSearch = if (isSearchStarting) {
            _cryptoList.value.data ?: emptyList()
        } else {
            initialCryptoList ?: emptyList()
        }
        if (query.isEmpty()) {
            _cryptoList.value = Resource.Success(initialCryptoList ?: emptyList())
            isSearchStarting = true
            return
        }
        val results = listToSearch.filter {
            it.symbol.contains(query.trim(), ignoreCase = true)
        }
        if (isSearchStarting) {
            initialCryptoList = _cryptoList.value.data
            isSearchStarting = false
        }
        _cryptoList.value = Resource.Success(results)
    }
    private fun loadCryptos() {
        _isLoading.value = true
        _errorMessage.value = ""
        viewModelScope.launch {
            try {
                cryptoListUseCase.getCryptoList().collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            val cryptoItems = result.data?.mapIndexed { index, item ->
                                CryptoAllListItem(
                                    item.beta_value, item.circulating_supply, item.first_data_at, item.id,
                                    item.last_updated, item.max_supply, item.name, item.quotes, item.rank, item.symbol, item.total_supply
                                )
                            } ?: emptyList()
                            initialCryptoList = cryptoItems
                            _cryptoList.value = Resource.Success(cryptoItems)
                        }
                        is Resource.Error -> {
                            _errorMessage.value = result.message ?: "Error occurred!"
                            _cryptoList.value = Resource.Error(result.message ?: "Error occurred!", null)
                        }
                        is Resource.Loading -> {
                            _cryptoList.value = Resource.Loading()
                        }
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred!"
                _cryptoList.value = Resource.Error("Error occurred!", null)
            }
            _isLoading.value = false
        }
    }
    fun refreshCryptoList() {
        val currentCryptoList = _cryptoList.value.data
        _cryptoList.value = Resource.Success(currentCryptoList ?: emptyList())
    }
    fun saveCryptoListToDB(localCryptoList: LocalCryptoList) {
        viewModelScope.launch {
            cryptoListUseCase.saveCryptoList(localCryptoList)
        }
    }
    fun getSavedCryptoListFromDB(): Flow<Resource<List<CryptoAllListItem>>> {
        return cryptoListUseCase.getSavedCryptoList()
    }
}
