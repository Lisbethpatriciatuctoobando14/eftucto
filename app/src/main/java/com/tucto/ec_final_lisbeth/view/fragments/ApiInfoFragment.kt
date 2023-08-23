package com.tucto.ec_final_lisbeth.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tucto.ec_final_lisbeth.R
import com.tucto.ec_final_lisbeth.databinding.FragmentApiInfoBinding
import com.tucto.ec_final_lisbeth.view.LoginActivity


class ApiInfoFragment : Fragment() {

    private lateinit var binding: FragmentApiInfoBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApiInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = Firebase.auth
        val currentUser = firebaseAuth.currentUser
        binding.txtusuario.text = "Usuario: ${currentUser?.email}"

        binding.btnSalir.setOnClickListener {
            firebaseAuth.signOut()
            navigateToLogin()
        }
    }
    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }


}