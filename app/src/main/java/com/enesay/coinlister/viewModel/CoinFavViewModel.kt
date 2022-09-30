package com.enesay.coinlister.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.enesay.coinlister.model.favorites
import com.enesay.coinlister.data.CoinDatabase
import kotlinx.coroutines.launch

class CoinFavViewModel(application: Application):BaseViewModel(application) {
  val favCoinLiveData=MutableLiveData<List<favorites>>()

    fun getFavCoins(){

        launch {
            val dao= CoinDatabase(getApplication()).coinDao()
            val favList= dao.getAllFavCoin()
            showFavCoins(favList)
        }
    }

    fun insertFavCoins(FavCoin:favorites) {

        launch {
            val dao = CoinDatabase(getApplication()).coinDao()
            dao.insertFavCoin(FavCoin)
        }
    }

    fun deleteFavCoin(id:Int){
        launch {
            val dao= CoinDatabase(getApplication()).coinDao()
            dao.deleteFavCoin(id)
            showFavCoins(dao.getAllFavCoin())

        }
    }
    fun deleteAllFavCoins(){
        launch {
            val dao= CoinDatabase(getApplication()).coinDao()
            dao.updateAllFavSit(false)
            dao.deleteAllFavCoins()
            showFavCoins(dao.getAllFavCoin())

        }
    }
    fun showFavCoins(favList: List<favorites>) {
        favCoinLiveData.value=favList
    }

    fun updateFavPrices(){

        launch {
            val dao= CoinDatabase(getApplication()).coinDao()

            val liste=dao.getAllFavCoin()

            for (i in 0..liste.size-1){
                dao.updateFavPrices(liste.get(i).fav_id,dao.getSpecificprices(liste.get(i).fav_id))

            }
            val list=dao.getAllFavCoin()

            showFavCoins(list)
        }
    }





}