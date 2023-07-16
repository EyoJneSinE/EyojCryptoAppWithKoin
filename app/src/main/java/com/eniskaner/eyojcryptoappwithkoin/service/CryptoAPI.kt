package com.eniskaner.eyojcryptoappwithkoin.service

import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoAPI {
    //https://api.coinpaprika.com/v1/tickers
    @GET("tickers")
    suspend fun getCyrptoList() : Unit

    //https://api.coinpaprika.com/v1/coins/btc-bitcoin
    @GET("coins/{id}")
    suspend fun getCyrpto(
        @Path("id") id : String
    ) : Unit
}