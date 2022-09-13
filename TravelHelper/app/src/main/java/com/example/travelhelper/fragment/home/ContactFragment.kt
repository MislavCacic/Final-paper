package com.example.travelhelper.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction
import com.example.travelhelper.R
import com.example.travelhelper.databinding.FragmentContactBinding
import com.example.travelhelper.databinding.FragmentHomeBinding
import com.example.travelhelper.fragment.BaseFragment

class ContactFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = ContactFragment()
    }

    private var _binding: FragmentContactBinding? = null
    private val binding: FragmentContactBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }
}