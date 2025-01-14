package com.eniskaner.eyojcryptoappwithkoin.network.service

import com.eniskaner.eyojcryptoappwithkoin.network.model.Crypto
import com.eniskaner.eyojcryptoappwithkoin.network.model.CryptoAllListItem
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoAPI {
    //https://api.coinpaprika.com/v1/tickers
    @GET("tickers")
    suspend fun getCyrptoList() : List<CryptoAllListItem>

    //https://api.coinpaprika.com/v1/coins/btc-bitcoin
    @GET("coins/{id}")
    suspend fun getCyrpto(
        @Path("id") id : String
    ) : Crypto
}