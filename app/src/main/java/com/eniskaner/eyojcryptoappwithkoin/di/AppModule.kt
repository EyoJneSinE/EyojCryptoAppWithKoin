package com.eniskaner.eyojcryptoappwithkoin.di

import com.eniskaner.eyojcryptoappwithkoin.repo.CryptoRepository
import com.eniskaner.eyojcryptoappwithkoin.service.CryptoAPI
import com.eniskaner.eyojcryptoappwithkoin.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCyrptoRepository(
        api: CryptoAPI
    ) = CryptoRepository(api)

    @Singleton
    @Provides
    fun provideCyrptoApi() : CryptoAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CryptoAPI::class.java)
    }
}