package com.enesay.coinlister.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.enesay.coinlister.R
import com.enesay.coinlister.model.Data
import com.enesay.coinlister.model.favorites
import com.enesay.coinlister.view.HostFragmentDirections
import com.enesay.coinlister.viewModel.CoinFavViewModel
import com.enesay.coinlister.viewModel.CoinListViewModel
import kotlinx.android.synthetic.main.recycler_row.view.*

class recycler_adapter(val coinList: ArrayList<Data>) :
    RecyclerView.Adapter<recycler_adapter.CoinsVH>() {
    private lateinit var viewmodel: CoinListViewModel
    private lateinit var viewmodel_fav: CoinFavViewModel
    lateinit var sharedPreferences: SharedPreferences


    class CoinsVH(itemview: View) : RecyclerView.ViewHolder(itemview) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row, parent, false)
        sharedPreferences = parent.context?.getSharedPreferences(
            "com.enesay.coinlister.adapter",
            Context.MODE_PRIVATE
        )!!

        return CoinsVH(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CoinsVH, position: Int) {

        viewmodel = CoinListViewModel(Application())
        viewmodel_fav = CoinFavViewModel(Application())

        holder.itemView.tv_coin_name.text = coinList[position].name
        holder.itemView.tv_coin_price.text = "$%.2f".format(coinList[position].quote!!.USD.price)

        holder.itemView.setOnClickListener {
            val action =
                HostFragmentDirections.actionHostFragmentToCoinDetailFragment(coinList.get(position).id)
            Navigation.findNavController(it).navigate(action)
        }

        holder.itemView.checkbox_fav.setOnClickListener {
            if (holder.itemView.checkbox_fav.isChecked) {

                val favCoin = favorites(
                    coinList.get(position).id,
                    coinList.get(position).name,
                    coinList.get(position).quote!!.USD.price
                )
                viewmodel_fav.insertFavCoins(favCoin)
                viewmodel.updateCheck(true, coinList.get(position).id, holder.itemView.context)

            } else {
                viewmodel_fav.deleteFavCoin(coinList.get(position).id)

                viewmodel.updateCheck(false, coinList.get(position).id, holder.itemView.context)

            }

        }

        holder.itemView.checkbox_fav.isChecked = coinList.get(position).kontrol

    }

    override fun getItemCount(): Int {

        return coinList.size
    }

    fun refreshCoinList(newCoinList: List<Data>) {
        coinList.clear()
        coinList.addAll(newCoinList)
        notifyDataSetChanged()
    }

}







