package com.example.travelhelper.fragment.route.sights

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelhelper.activity.DisplayRouteActivity
import com.example.travelhelper.activity.MainActivity
import com.example.travelhelper.databinding.FragmentListOfSightsBinding
import com.example.travelhelper.fragment.BaseFragment
import com.example.travelhelper.model.Architecture
import com.example.travelhelper.model.Route
import com.example.travelhelper.util.SightsAdapter
import com.example.travelhelper.viewModel.SightsViewModel

class ListOfSightsFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = ListOfSightsFragment()
        const val ROUTE = "route"
        const val ARCHITECTURE = "architecture"
        const val LOG_TAG = "ListOfSightsFragment"
    }

    private var _binding: FragmentListOfSightsBinding? = null
    private val binding: FragmentListOfSightsBinding
        get() = _binding!!

    private val vm: SightsViewModel by viewModels()

    private lateinit var sightsAdapter: SightsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListOfSightsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        observeLiveData()
        onClickListener()
        (requireActivity() as? MainActivity)?.currentUserLocation?.let {
            vm.getSights(this.arguments?.getSerializable(ARCHITECTURE) as Architecture, it)
        } ?: Log.d(LOG_TAG, "Current user location isn't initialized" )

    }

    private fun onClickListener() {
        binding.btnCreateRoute.setOnClickListener {
            val route = Route(sightsAdapter.getSelectedSights())
            val intent = Intent(requireContext(), DisplayRouteActivity::class.java)
            intent.putExtra(ROUTE, route)
            startActivity(intent)
        }
    }

    private fun observeLiveData() {
        vm.getSightsSuccess.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                sightsAdapter.setSights(it)
                binding.rvSights.visibility = View.VISIBLE
                binding.tvEmptyList.visibility = View.GONE
            } else {
                binding.rvSights.visibility = View.GONE
                binding.tvEmptyList.visibility = View.VISIBLE
            }
        }

        vm.getSightsFail.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(), "Retrieving sights failed: $it",
                Toast.LENGTH_SHORT
            ).show()
        }

        vm.getSightsArchitecture.observe(viewLifecycleOwner) {
            binding.tvSightsArch.text = it.replaceFirstChar { it.titlecaseChar() }
        }
    }

    private fun initRecycler() {
        sightsAdapter = SightsAdapter(vm = vm)

        binding.rvSights.apply {
            adapter = sightsAdapter
            layoutManager = LinearLayoutManager(binding.root.context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }
}