package com.enesay.coinlister.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import com.enesay.coinlister.R
import kotlinx.android.synthetic.main.activity_main.*


class Splash : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController(requireView()).navigate(R.id.loginFragment)
        }, 1550)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

}