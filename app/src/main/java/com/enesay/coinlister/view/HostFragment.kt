package com.enesay.coinlister.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.enesay.coinlister.R
import com.enesay.coinlister.databinding.FragmentHostBinding
import com.enesay.coinlister.databinding.FragmentRegisterBinding
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_coin_detail.view.*
import kotlinx.android.synthetic.main.fragment_coin_list.*
import kotlinx.android.synthetic.main.fragment_host.*
import kotlinx.android.synthetic.main.top_bar.*


class HostFragment : Fragment() {
    private lateinit var binding: FragmentHostBinding
    private var activeFragment: Fragment = CoinListFragment(true)
    private var check = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentHostBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationView.itemIconTintList = null

        arguments.let {
            if (it != null) {
                check = HostFragmentArgs.fromBundle(it).check
            }
        }
        if (check == 1) {
            bottomNavigationView.menu.findItem(R.id.btn_favs).isChecked = true
            replaceFragments(CoinFavFragment())
        } else {
            replaceFragments(activeFragment)
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.btn_list -> {
                    replaceFragments(CoinListFragment(true))
                    activeFragment = CoinListFragment(true)

                    true
                }
                R.id.btn_favs -> {
                    replaceFragments(CoinFavFragment())
                    activeFragment = CoinFavFragment()

                    true
                }
                else -> false
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object
            : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                alertDialogCreator()

            }
        })
    }

    private fun alertDialogCreator() {

        val alertDialog = AlertDialog.Builder(context)

        alertDialog.setTitle("Do you want to exit? ")
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

    private fun replaceFragments(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.frameLayout.id, fragment)
        fragmentTransaction.commit()
    }


}