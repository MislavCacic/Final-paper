package com.example.travelhelper.fragment.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelhelper.databinding.FragmentBuildRouteBinding
import com.example.travelhelper.fragment.BaseFragment
import com.example.travelhelper.fragment.route.sights.ListOfSightsFragment
import com.example.travelhelper.model.Architecture

class BuildRouteFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = BuildRouteFragment()
        const val ARCHITECTURE = "architecture"
    }

    private var _binding: FragmentBuildRouteBinding? = null
    private val binding: FragmentBuildRouteBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuildRouteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
    }

    private fun navigateToWithBundle(architecture: Architecture) {
        val fragment = ListOfSightsFragment.newInstance()
        val bundle = Bundle()
        bundle.putSerializable(ARCHITECTURE, architecture)
        fragment.arguments = bundle
        navigateTo(fragment)
    }

    private fun onClickListener() {
        binding.apply {
            btnAll.setOnClickListener {
                navigateToWithBundle(Architecture.ALL)
            }

            btnBaroque.setOnClickListener {
                navigateToWithBundle(Architecture.BAROQUE)
            }

            btnSecession.setOnClickListener {
                navigateToWithBundle(Architecture.SECESSION)
            }

            btnModern.setOnClickListener {
                navigateToWithBundle(Architecture.MODERN)
            }

            btnGothic.setOnClickListener {
                navigateToWithBundle(Architecture.GOTHIC)
            }
        }
    }
}