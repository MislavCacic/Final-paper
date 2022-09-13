package com.example.travelhelper.fragment.route.sights

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.travelhelper.R
import com.example.travelhelper.databinding.FragmentAddSightsBinding
import com.example.travelhelper.fragment.BaseFragment
import com.example.travelhelper.fragment.home.HomeFragment
import com.example.travelhelper.model.Sight
import com.example.travelhelper.util.LocationProvider
import com.example.travelhelper.viewModel.SightsViewModel
import kotlin.math.roundToInt

class AddSightsFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AddSightsFragment()
    }

    private var _binding: FragmentAddSightsBinding? = null
    private val binding: FragmentAddSightsBinding
        get() = _binding!!

    private val architectures = arrayListOf("gothic", "baroque", "secession", "modern")

    private lateinit var architecture: String

    private lateinit var imageUri: Uri

    private val vm: SightsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSightsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
        initArchitectureSpinner()
        observeLiveData()
    }

    private fun observeLiveData() {
        vm.addSightSuccess.observe(viewLifecycleOwner) {
            navigateTo(fragment = HomeFragment.newInstance())
        }

        vm.addSightFail.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(), "Adding sight failed: $it",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initArchitectureSpinner() {
        binding.spinnerArchitecture.apply {
            adapter = ArrayAdapter(
                requireContext(),
                R.layout.item_spinner,
                resources.getStringArray(R.array.Architectures)
            )
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        position: Int,
                        p3: Long
                    ) {
                        architecture = architectures[position]
                    }
                }
        }
    }

    private fun onClickListener() {
        binding.btnUpload.setOnClickListener {
            LocationProvider.getLocationFromAddress(
                requireContext(),
                binding.etAddress.text.toString()
            ) {
                val sight = Sight(
                    name = binding.etName.text.toString(),
                    description = binding.etDescription.text.toString(),
                    location = it,
                    architecture = architecture,
                    rating = binding.ratingBar.rating.roundToInt()
                )
                vm.addSight(imageUri, sight)
            }
        }
        binding.btnChoosePicture.setOnClickListener {
            openGalleryForImage()
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let {
                    imageUri = it
                    binding.ivPhoto.setImageURI(imageUri)
                }
            }
        }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }
}