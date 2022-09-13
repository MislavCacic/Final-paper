package com.example.travelhelper.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.travelhelper.R
import com.example.travelhelper.databinding.FragmentHomeBinding
import com.example.travelhelper.databinding.FragmentProfileBinding
import com.example.travelhelper.fragment.BaseFragment
import com.example.travelhelper.viewModel.LoginRegistrationViewModel
import com.google.firebase.database.*

class ProfileFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding!!

    private val vm: LoginRegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProfile()
    }

    private fun setupProfile() {
        val currentUser = vm.getCurrentUser()
        binding.userEmail.text = currentUser?.email
        binding.usernameOnProfile.text = currentUser?.displayName
    }
}