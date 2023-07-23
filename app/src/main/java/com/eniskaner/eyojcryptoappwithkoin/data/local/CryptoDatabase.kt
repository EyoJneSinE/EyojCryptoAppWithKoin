package com.eniskaner.eyojcryptoappwithkoin.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCrypto
import com.eniskaner.eyojcryptoappwithkoin.data.model.LocalCryptoList

@Database(
    entities = [LocalCryptoList::class, LocalCrypto::class],
    version = 1,
    exportSchema = false
)
abstract class CryptoDatabase: RoomDatabase() {
    abstract fun getCryptoDao(): CryptoDao
    companion object {
        @Volatile
        private var instance: CryptoDatabase? = null
        fun getDatabase(context: Context): CryptoDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, CryptoDatabase::class.java, "crypto_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}