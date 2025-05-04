package com.example.tbcexercises.presentation.detail_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentDetailBinding
import com.example.tbcexercises.presentation.detail_screen.company_prices_adapter.CompanyPricesListAdapter
import com.example.tbcexercises.utils.extension.collectLastState
import com.example.tbcexercises.utils.extension.loadImg
import com.example.tbcexercises.utils.extension.toast
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

        setupRecyclerView()
        observeUiState()

        viewModel.getProduct(args.productID)
    }

    private fun setupRecyclerView() {
        binding.rvCompanyPrices.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = companyPricesListAdapter
        }
    }

    private fun observeUiState() {
        collectLastState(viewModel.uiState) { state ->
            updateUI(state)
        }
    }

    private fun updateUI(state: DetailScreenUiState) {
        binding.apply {
            progressBar.isVisible = state.isLoading

            state.error?.let {
                toast(it)
            }

            if (!state.isLoading && state.error == null && state.products.isNotEmpty()) {
                val product = state.products.first()
                txtProductName.text = product.productName
                imgProduct.loadImg(product.productImgUrl)
                companyPricesListAdapter.submitList(state.products)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
