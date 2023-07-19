package com.eniskaner.eyojcryptoappwithkoin

import android.app.Application
import com.eniskaner.eyojcryptoappwithkoin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EyojCryptoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EyojCryptoApplication)
            modules(appModule)
        }
    }
}