package com.enesay.coinlister.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.enesay.coinlister.R
import com.enesay.coinlister.databinding.FragmentLoginBinding
import com.enesay.coinlister.databinding.FragmentRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object
            : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                activity!!.finish()

            }
        })

        binding.btnLogin.setOnClickListener {

            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (!email.equals("") && !password.equals("")) {

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    requireActivity(),
                    object : OnCompleteListener<AuthResult?> {
                        override fun onComplete(p0: Task<AuthResult?>) {
                            if (p0.isSuccessful) {
                                val action =
                                    LoginFragmentDirections.actionLoginFragmentToHostFragment()
                                Navigation.findNavController(it).navigate(action)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    p0.exception?.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    })
            } else {
                Toast.makeText(
                    requireContext(),
                    "please check your email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val action = LoginFragmentDirections.actionLoginFragmentToHostFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
    }


}