package com.example.travelhelper.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.travelhelper.databinding.FragmentHomeBinding
import com.example.travelhelper.fragment.BaseFragment
import com.example.travelhelper.fragment.route.BuildRouteFragment
import com.example.travelhelper.fragment.loginRegistration.TravelHelperFragment
import com.example.travelhelper.fragment.route.sights.AddSightsFragment
import com.example.travelhelper.viewModel.LoginRegistrationViewModel

class HomeFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val vm: LoginRegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
    }

    private fun onClickListener() {
       binding.apply {
           btnProfile.setOnClickListener {
               navigateTo(ProfileFragment.newInstance())
           }

           btnAddSights.setOnClickListener {
               navigateTo(AddSightsFragment.newInstance())
           }

           btnBuildRoute.setOnClickListener {
               navigateTo(BuildRouteFragment.newInstance())
           }

           btnContact.setOnClickListener {
               navigateTo(ContactFragment.newInstance())
           }

           btnLogout.setOnClickListener {
               vm.signOut()
               navigateTo(TravelHelperFragment.newInstance())
           }
       }
    }


}