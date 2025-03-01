package com.example.tbcexercises.presentation.cart_screen

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentCartBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.cart_screen.cart_product_adapter.CartProductListAdapter
import com.example.tbcexercises.presentation.cart_screen.companies_adapter.CompanyListAdapter
import com.example.tbcexercises.utils.extension.collectLastState
import com.example.tbcexercises.utils.network_helper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val viewModel: CartViewModel by viewModels()

    private val companyListAdapter by lazy {
        CompanyListAdapter(onCompanyClick = { viewModel.selectCompany(it.id) })
    }

    private val cartProductAdapter by lazy {
        CartProductListAdapter(onRemoveClick = {
            viewModel.deleteProduct(it)
        }, onDecreaseClick = {
            viewModel.decrementQuantity(it)
        }, onIncreaseClick = {
            viewModel.incrementQuantity(it)
        })
    }

    override fun start() {
        setUpCartProductAdapter()
        setUpCompanyAdapter()
        collectLastState(viewModel.cartProducts) { state ->
            when (state) {
                is Resource.Error -> {}
                Resource.Loading -> {}
                is Resource.Success -> {
                    cartProductAdapter.submitList(state.data.toList())

                    binding.txtTotalPrice.text =
                        state.data.sumOf { it.totalPrice }.toFloat().toString()
                }

                null -> {}
            }
        }

        collectLastState(viewModel.companies) { state ->
            when (state) {
                is Resource.Error -> {}
                Resource.Loading -> {}
                is Resource.Success -> {
                    Log.d("dataas", state.data.toList().toString())
                    companyListAdapter.submitList(state.data.toList())
                }

                null -> {}
            }
        }

    }

    private fun setUpCompanyAdapter() {
        binding.rvCompanies.apply {
            adapter = companyListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpCartProductAdapter() {
        binding.rvCartProducts.apply {
            adapter = cartProductAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}