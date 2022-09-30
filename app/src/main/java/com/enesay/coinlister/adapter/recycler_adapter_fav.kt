package com.enesay.coinlister.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.enesay.coinlister.R
import com.enesay.coinlister.model.favorites
import com.enesay.coinlister.view.HostFragmentDirections
import com.enesay.coinlister.viewModel.CoinFavViewModel
import com.enesay.coinlister.viewModel.CoinListViewModel
import kotlinx.android.synthetic.main.fav_recycler_row.view.*

class recycler_adapter_fav(val favCoinList: ArrayList<favorites>) :
    RecyclerView.Adapter<recycler_adapter_fav.FavVh>() {
    private lateinit var viewmodel_fav: CoinFavViewModel
    private lateinit var viewmodel: CoinListViewModel

    class FavVh(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavVh {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fav_recycler_row, parent, false)
        return FavVh(view)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: FavVh, position: Int) {
        viewmodel_fav = CoinFavViewModel(Application())
        viewmodel = CoinListViewModel(Application())

        holder.itemView.tv_fav_coin_name.text = favCoinList.get(position).name
        holder.itemView.tv_fav_coin_price.text = "$%.2f".format(favCoinList[position].price)

        holder.itemView.btn_fav_delete.setOnClickListener {
            viewmodel_fav.deleteFavCoin(favCoinList.get(position).fav_id)
            viewmodel.updateCheck(false, favCoinList.get(position).fav_id, holder.itemView.context)
            favCoinList.remove(favCoinList.get(position))
            notifyDataSetChanged()
        }
        holder.itemView.setOnClickListener {
            val action =
                HostFragmentDirections.actionHostFragmentToCoinDetailFragment(
                    favCoinList.get(
                        position
                    ).fav_id, 1
                )
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return favCoinList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addFavCoinList(newCoinList: List<favorites>) {
        favCoinList.clear()
        favCoinList.addAll(newCoinList)
        notifyDataSetChanged()
    }

}