package com.example.tbcexercises.presentation.detail_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentDetailBinding
import com.example.tbcexercises.presentation.detail_screen.company_prices_adapter.CompanyPricesListAdapter
import com.example.tbcexercises.utils.GlideImageLoader
import com.example.tbcexercises.utils.network_helper.Resource
import com.example.tbcexercises.utils.extension.collectLastState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : DialogFragment() {

    private lateinit var args: DetailFragmentArgs
    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    private val companyPricesListAdapter by lazy {
        CompanyPricesListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = DetailFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCompanyPrices.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = companyPricesListAdapter
        }
        collectLastState(viewModel.productDetailState) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

                    if (resource.data.isNotEmpty()) {
                        val product = resource.data.first()
                        binding.txtProductName.text = product.productName
                        GlideImageLoader.loadImage(binding.imgProduct, product.productImgUrl)
                        companyPricesListAdapter.submitList(resource.data)
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE

                }
            }
        }
        Log.d("productId", args.productID.toString())
        viewModel.getProduct(args.productID)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
