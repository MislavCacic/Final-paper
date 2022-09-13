package com.example.travelhelper.fragment.loginRegistration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.travelhelper.databinding.FragmentRegistrationBinding
import com.example.travelhelper.fragment.BaseFragment
import com.example.travelhelper.fragment.home.HomeFragment
import com.example.travelhelper.viewModel.LoginRegistrationViewModel

class RegistrationFragment : BaseFragment() {

    companion object {
        const val TAG = "RegistrationFragment"

        @JvmStatic
        fun newInstance() = RegistrationFragment()
    }

    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding
        get() = _binding!!

    private val vm: LoginRegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        onClickListener()
    }

    private fun onClickListener() {
        binding.registerButton.setOnClickListener {
            vm.registerUser(
                email = binding.enterEmail.text.toString(),
                password = binding.enterPassword.text.toString(),
                username = binding.chooseUsername.text.toString()
            )
        }
    }

    private fun observeLiveData() {
        vm.registrationSuccess.observe(viewLifecycleOwner) {
            navigateTo(fragment = HomeFragment.newInstance())
        }

        vm.registrationFail.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(), "Registration failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}