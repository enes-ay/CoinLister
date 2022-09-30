package com.enesay.coinlister.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesay.coinlister.R
import com.enesay.coinlister.adapter.recycler_adapter
import com.enesay.coinlister.databinding.FragmentCoinListBinding
import com.enesay.coinlister.databinding.FragmentRegisterBinding
import com.enesay.coinlister.viewModel.CoinFavViewModel
import com.enesay.coinlister.viewModel.CoinListViewModel
import com.google.android.material.animation.Positioning
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_coin_list.*


class CoinListFragment(enabled: Boolean) : Fragment() {
    private lateinit var binding: FragmentCoinListBinding
    private lateinit var viewmodel: CoinListViewModel
    private lateinit var viewmodel_fav: CoinFavViewModel
    private val recyclerAdapter = recycler_adapter(arrayListOf())
    lateinit var recyclerScrollKey: String

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCoinListBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("CommitPrefEdits", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = recyclerAdapter
        val tempViewModel: CoinListViewModel by viewModels()
        viewmodel = tempViewModel

        val tempViewModel_fav: CoinFavViewModel by viewModels()
        viewmodel_fav = tempViewModel_fav

        sharedPreferences = context?.getSharedPreferences(
            "com.enesay.coinlister.adapter",
            Context.MODE_PRIVATE
        )!!

        viewmodel.refreshData()
        observeLiveData()

        binding.btnLogout.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)

            alertDialog.setTitle("Do you want to logout?")
            alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener(
                { dialog, which ->
                    FirebaseAuth.getInstance().signOut()
                    findNavController().navigate(R.id.action_hostFragment_to_loginFragment)

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



        binding.swipeRefreshLayout.setOnRefreshListener {

            binding.progressBar.visibility = View.VISIBLE
            binding.tvErrorMessage.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
            viewmodel.refreshWebDataforSwipe()
            binding.swipeRefreshLayout.isRefreshing = false

        }


    }

    fun observeLiveData() {

        viewmodel.coins.observe(viewLifecycleOwner, Observer {

            it.let {
                binding.recyclerView.visibility = View.VISIBLE
                recyclerAdapter.refreshCoinList(it)
            }

        })
        viewmodel.coinErrorMessage.observe(viewLifecycleOwner, Observer {

            it.let {
                if (it) {
                    binding.recyclerView.visibility = View.GONE
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE

                } else {
                    binding.tvErrorMessage.visibility = View.GONE
                }
            }
        })

        viewmodel.coinIsLoading.observe(viewLifecycleOwner, Observer {
            it.let {

                if (it) {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.tvErrorMessage.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                } else {

                    binding.progressBar.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE


                }
            }
        })
    }


}


