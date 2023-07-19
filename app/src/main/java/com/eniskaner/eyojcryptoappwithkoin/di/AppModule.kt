package com.eniskaner.eyojcryptoappwithkoin.di

import com.eniskaner.eyojcryptoappwithkoin.repo.CryptoRepository
import com.eniskaner.eyojcryptoappwithkoin.repo.CryptoRepositoryImplementation
import com.eniskaner.eyojcryptoappwithkoin.service.CryptoAPI
import com.eniskaner.eyojcryptoappwithkoin.util.Constants.BASE_URL
import com.eniskaner.eyojcryptoappwithkoin.viewmodel.CryptoDetailViewModel
import com.eniskaner.eyojcryptoappwithkoin.viewmodel.CryptoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    //creates a singleton
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)
    }

    single<CryptoRepository> {
        //since we defined retrofit above, this repository will asks for retrofit and we can simply
        //say get() in order to inject it even here
        CryptoRepositoryImplementation(get())

        //since we are injecting the abstraction, we should explicitly state the
        //implementation and abstraction here
    }

    viewModel {
        //since we defined repo above, we can call get() here as well
        CryptoListViewModel(get())
    }
    viewModel {
        CryptoDetailViewModel(get())
    }
}