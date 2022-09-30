package com.enesay.coinlister.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesay.coinlister.R
import com.enesay.coinlister.adapter.recycler_adapter_fav
import com.enesay.coinlister.databinding.FragmentCoinFavBinding
import com.enesay.coinlister.databinding.FragmentCoinListBinding
import com.enesay.coinlister.viewModel.CoinFavViewModel
import com.enesay.coinlister.viewModel.CoinListViewModel
import kotlinx.android.synthetic.main.fragment_coin_fav.*


class CoinFavFragment : Fragment() {
    private lateinit var binding: FragmentCoinFavBinding
    private lateinit var viewmodel_fav: CoinFavViewModel
    private lateinit var viewmodel: CoinListViewModel
    private var recyclerAdapter_fav = recycler_adapter_fav(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCoinFavBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tempViewModel: CoinListViewModel by viewModels()
        viewmodel = tempViewModel
        val tempViewModelFav: CoinFavViewModel by viewModels()
        viewmodel_fav = tempViewModelFav

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFav.layoutManager = layoutManager
        binding.recyclerViewFav.adapter = recyclerAdapter_fav

        viewmodel_fav.getFavCoins()
        observeFavCoinData()

        binding.swipeRefreshLayoutFav.setOnRefreshListener {
            swipeRefreshLayout_fav.isRefreshing = false
            viewmodel_fav.updateFavPrices()
        }

        binding.btnDeleteFavs.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)

            alertDialog.setTitle("Do you want to delete all favorites?")
            alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener(
                { dialog, which ->
                    viewmodel_fav.deleteAllFavCoins()

                }
            ))
            alertDialog.setNegativeButton("No", DialogInterface.OnClickListener(
                { dialog, which ->

                }
            ))
            alertDialog.setCancelable(false)
            alertDialog.setIcon(R.drawable.ic_dialog)
            alertDialog.create()
            alertDialog.show()

        }

    }

    fun observeFavCoinData() {
        viewmodel_fav.favCoinLiveData.observe(viewLifecycleOwner, Observer {
            recyclerAdapter_fav.addFavCoinList(it)

        })
    }
    //
//
//        OnBackPressedDispatcher().addCallback(
//            viewLifecycleOwner,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    val action = CoinFavFragmentDirections.actionFavFragmentToHostFragment()
//                    Navigation.findNavController(view).navigate(action)
//
//                }
//            })


}