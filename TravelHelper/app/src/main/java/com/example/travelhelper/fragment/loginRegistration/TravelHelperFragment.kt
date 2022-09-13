package com.example.travelhelper.fragment.loginRegistration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.travelhelper.R
import com.example.travelhelper.databinding.FragmentLoginBinding
import com.example.travelhelper.databinding.FragmentTravelHelperBinding
import com.example.travelhelper.fragment.BaseFragment
import com.example.travelhelper.fragment.home.HomeFragment
import com.example.travelhelper.viewModel.LoginRegistrationViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class TravelHelperFragment : BaseFragment() {

    companion object {
        const val TAG = "TravelHelperFragment"
        @JvmStatic
        fun newInstance() = TravelHelperFragment()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null)
        val currentUser = vm.getCurrentUser()
        Log.d(TAG, "Current user: ${currentUser?.email}")
        if(currentUser != null) {
            navigateTo(HomeFragment.newInstance())
        }
    }

    private var _binding: FragmentTravelHelperBinding? = null
    private val binding: FragmentTravelHelperBinding
        get() = _binding!!

    private val vm: LoginRegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTravelHelperBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
    }

    private fun onClickListener() {
        binding.btnLogin.setOnClickListener {
            navigateTo(LoginFragment.newInstance())
        }
        binding.btnRegister.setOnClickListener {
            navigateTo(RegistrationFragment.newInstance())
        }
    }
}