package com.example.travelhelper.fragment.loginRegistration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.travelhelper.fragment.home.HomeFragment
import com.example.travelhelper.R
import com.example.travelhelper.databinding.FragmentLoginBinding
import com.example.travelhelper.fragment.BaseFragment
import com.example.travelhelper.viewModel.LoginRegistrationViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class LoginFragment : BaseFragment() {

    companion object {
        const val TAG = "LoginFragment"
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    private val vm: LoginRegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
        observeLiveData()
    }

    private fun observeLiveData() {
        vm.loginSuccess.observe(viewLifecycleOwner) {
            navigateTo(fragment = HomeFragment.newInstance())
        }

        vm.loginFail.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(), "Login failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onClickListener() {
        binding.loginButton.setOnClickListener {
            vm.loginUser(email = binding.enterEmail.text.toString(), password = binding.enterPassword.text.toString())
        }
    }

}