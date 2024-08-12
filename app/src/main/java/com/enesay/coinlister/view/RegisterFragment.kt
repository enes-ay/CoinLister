package com.enesay.coinlister.view

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.enesay.coinlister.R
import com.enesay.coinlister.databinding.FragmentRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.lang.Exception


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()



        binding.userRegister.setOnClickListener {
            val email = binding.userEmail.text.toString().trim()
            val password = binding.userPassword.text.toString().trim()

            if (!email.equals("") && !password.equals("")) {
                createUser(email, password)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please check your email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.userRegisterCancel.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    private fun createUser(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity(),
                OnCompleteListener {
                    if (it.isSuccessful) {

                        Toast.makeText(requireContext(), "Welcome!", Toast.LENGTH_SHORT).show()
                        auth.signInWithEmailAndPassword(email, password)
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                    } else {
                        Toast.makeText(
                            requireContext(),
                            it.exception?.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    println(it.exception?.message.toString())
                }
            )
    }

}

