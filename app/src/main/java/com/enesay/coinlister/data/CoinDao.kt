package com.enesay.coinlister.data

import androidx.room.*
import com.enesay.coinlister.model.Data
import com.enesay.coinlister.model.favorites

@Dao
interface CoinDao {

    // data object access

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg coin:Data)

    @Query("Select * From Data ")
    suspend fun getAllCoins():List<Data>

    @Query("Select * From Data Where id=:coinId")
    suspend fun getCoin(coinId:Int):Data

    @Query("Select price From Data Where id=:id")
    suspend fun getSpecificprices(vararg id:Int):Double

    @Query("delete from Data")
    suspend fun deleteAllCoins()

    @Transaction
    @Insert
    suspend fun insertFavCoin(vararg favCoin:favorites)

    @Query("Select * From  favorites")
    suspend fun getAllFavCoin():List<favorites>

    @Query("Select * From  Data where id=:id")
    suspend fun getDetails(id:Int):Data

    @Query("update Data set kontrol=:deger where id=:id ")
    suspend fun updateFavSit(deger:Boolean,id:Int)

    @Query("update Data set kontrol=:deger")
    suspend fun updateAllFavSit(deger:Boolean)

    @Query("delete from favorites where fav_id=:id ")
    suspend fun deleteFavCoin(id:Int)

    @Update
    suspend fun updateAllCoins(coins:List<Data>)

    @Query("update Data set price=:price where id=:id ")
    suspend fun updateAllCoinPrices(id:Int,price:Double)
    @Query("delete from favorites")
    suspend fun deleteAllFavCoins()

    @Query("update favorites set price=:price where fav_id=:id ")
    suspend fun updateFavPrices(id:Int,price:Double)

    @Query("Select fav_id From  favorites")
    suspend fun getFavIds():List<Int>

}