package com.enesay.coinlister.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.enesay.coinlister.R
import com.enesay.coinlister.databinding.FragmentCoinDetailBinding
import com.enesay.coinlister.databinding.FragmentCoinListBinding
import com.enesay.coinlister.viewModel.CoinDetailViewModel
import com.enesay.coinlister.viewModel.CoinListViewModel
import kotlinx.android.synthetic.main.fragment_coin_detail.*

class CoinDetailFragment : Fragment() {

    private lateinit var binding: FragmentCoinDetailBinding
    private lateinit var viewmodel:CoinDetailViewModel
    private lateinit var viewmodel_fav:CoinListViewModel
    private var coinId=0
    private var control=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCoinDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tempViewmodel: CoinDetailViewModel by viewModels()
        viewmodel=tempViewmodel

        arguments.let {
            if (it != null) {
                coinId=CoinDetailFragmentArgs.fromBundle(it).coinID
                control=CoinDetailFragmentArgs.fromBundle(it).control
            }
        }
            activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,object
                : OnBackPressedCallback(true
            ) {
                override fun handleOnBackPressed() {
                    if (control==1){
                        val action=CoinDetailFragmentDirections.actionCoinDetailFragmentToHostFragment(1)
                        Navigation.findNavController(view).navigate(action)

                    }
                    else{
                        val action=CoinDetailFragmentDirections.actionCoinDetailFragmentToHostFragment()
                        Navigation.findNavController(view).navigate(action)
                    }

                }
            })

        observeLiveData()
        viewmodel.getDetails(coinId)
    }

    @SuppressLint("SetTextI18n")
    fun observeLiveData(){
        viewmodel.CoinDetailLiveData.observe(viewLifecycleOwner, Observer {

            it.let {
                binding.tvDtCoinName.text=it.name
                binding.tvDtCoinPrice.text="$%.2f".format(it.quote!!.USD.price)
                binding.tvDtVolume.text="$%.0f".format(it.quote!!.USD.volume_24h)
                binding.coinSymbol.text=it.symbol

                val supply:Double

                if(it.total_supply/1000000000>=1){
                     supply=it.total_supply/1000000000
                    binding.tvDtCoinSuply.text="$%.1f".format(supply)+"B"
                }
                else if(it.total_supply/1000000000<1&&it.total_supply/1000000>=1){
                    supply=it.total_supply/1000000
                    binding.tvDtCoinSuply.text="$%.1f".format(supply)+"M"

                }
                else {
                    supply=it.total_supply/1000
                    binding.tvDtCoinSuply.text="$%.1f".format(supply)+"K"

                }

            }

        })
    }
}