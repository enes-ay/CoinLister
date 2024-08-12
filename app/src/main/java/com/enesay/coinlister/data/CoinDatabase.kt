package com.enesay.coinlister.data

import android.content.Context
import androidx.room.*
import com.enesay.coinlister.model.Data
import com.enesay.coinlister.model.favorites


@Database(entities = arrayOf(Data::class,favorites::class), version = 1)
abstract class CoinDatabase: RoomDatabase() {

    abstract fun coinDao(): CoinDao


    companion object {

        private var lock = Any()

        @Volatile
        private var instance: CoinDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: databaseCreate(context).also {
                instance = it
            }
        }

        private fun databaseCreate(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CoinDatabase::class.java, "coindatabase"
            ).build()

    }
}