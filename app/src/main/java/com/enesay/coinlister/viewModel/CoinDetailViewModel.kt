package com.enesay.coinlister.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.enesay.coinlister.model.Data
import com.enesay.coinlister.data.CoinDatabase

import kotlinx.coroutines.launch

class CoinDetailViewModel(application: Application) : BaseViewModel(application) {

    val CoinDetailLiveData = MutableLiveData<Data>()

    fun getDetails(id: Int) {
        launch {
            val dao = CoinDatabase(getApplication()).coinDao()
            val data = dao.getDetails(id)
            showDetails(data)
        }
    }

    private fun showDetails(coinList: Data) {
        CoinDetailLiveData.value = coinList
    }

}