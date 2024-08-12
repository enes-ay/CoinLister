package com.enesay.coinlister.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.enesay.coinlister.model.Data
import com.enesay.coinlister.service.CoinAPIservice
import com.enesay.coinlister.data.CoinDatabase
import com.enesay.coinlister.util.TimeSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class CoinListViewModel(application: Application) : BaseViewModel(application) {

    val coins = MutableLiveData<List<Data>>()
    val coinErrorMessage = MutableLiveData<Boolean>()
    val coinIsLoading = MutableLiveData<Boolean>()
    private val updateTime = 10 * 60 * 1000 * 1000 * 1000L

    private val coinApiService = CoinAPIservice()
    private val disposable = CompositeDisposable()
    private val sharedPreferencesTime = TimeSharedPreferences(application)

    fun refreshData() {

        val submitDate = sharedPreferencesTime.getTime()

        if (submitDate != null && submitDate != 0L && System.nanoTime() - submitDate < updateTime) {
            getDataFromSqlite()

        } else {

            getDataFromWeb()
        }

    }

    fun refreshWebDataforSwipe() {
        getDataFromWeb()

    }

    private fun getDataFromWeb() {
        coinIsLoading.value = true

        disposable.add(
            coinApiService.getData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<com.enesay.coinlister.model.Response>() {
                    override fun onSuccess(t: com.enesay.coinlister.model.Response) {
                        coinErrorMessage.value = false
                        addSqlite(t.data)

                    }

                    override fun onError(e: Throwable) {
                        println(e.message.toString())
                    }

                })
        )


    }

    private fun getDataFromSqlite() {
        launch {
            val dao = CoinDatabase(getApplication()).coinDao()
            val coinList = dao.getAllCoins()
            showCoins(coinList)

        }
    }

    private fun addSqlite(coinList: List<Data>) {

        launch {
            val dao = CoinDatabase(getApplication()).coinDao()
            val favList = dao.getFavIds()
            updatePrices(coinList)
            //dao.insertAll(*coinList.toTypedArray())
            for (i in 0..favList.size - 1) {
                dao.updateFavSit(true, favList.get(i))
            }
            getAllCurrentCoins()
        }

        sharedPreferencesTime.saveTime(System.nanoTime())

    }

    private fun getAllCurrentCoins() {
        launch {
            val dao = CoinDatabase(getApplication()).coinDao()
            val newList = dao.getAllCoins()
            showCoins(newList)
        }
    }

    fun updatePrices(list: List<Data>) {
        launch {
            val dao = CoinDatabase(getApplication()).coinDao()
            for (i in 0..list.size - 1) {
                dao.updateAllCoinPrices(list.get(i).id, list.get(i).quote!!.USD.price)
            }
        }
    }

    private fun showCoins(coinList: List<Data>) {
        coins.value = coinList
        coinErrorMessage.value = false
        coinIsLoading.value = false
    }

    fun updateCheck(deger: Boolean, id: Int, context: Context) {
        launch {
            val dao = CoinDatabase(context).coinDao()
            dao.updateFavSit(deger, id)
            dao.getAllCoins()
        }
    }

}