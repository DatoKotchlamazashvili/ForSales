package com.example.tbcexercises.presentation.cart_screen

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentCartBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.cart_screen.cart_product_adapter.CartProductListAdapter
import com.example.tbcexercises.presentation.cart_screen.companies_adapter.CompanyListAdapter
import com.example.tbcexercises.utils.extension.collectLastState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val viewModel: CartViewModel by viewModels()

    private val companyListAdapter by lazy {
        CompanyListAdapter(onCompanyClick = { viewModel.selectCompany(it.company) })
    }

    private val cartProductAdapter by lazy {
        CartProductListAdapter(
            onRemoveClick = { viewModel.deleteProduct(it) },
            onDecreaseClick = { viewModel.decrementQuantity(it) },
            onIncreaseClick = { viewModel.incrementQuantity(it) }
        )
    }

    override fun start() {
        setUpCartProductAdapter()
        setUpCompanyAdapter()

        collectLastState(viewModel.uiState) {
            viewModel.uiState.collect { state ->
                updateUI(state)
            }
        }

        binding.txtError.text = getString(R.string.you_don_t_have_internet_connection)
    }


    private fun updateUI(state: CartScreenUiState) {
        val isOnline = state.isOnline ?: false
        binding.apply {
            rvCartProducts.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isNotEmpty() && isOnline
            rvCompanies.isVisible =
                !state.isLoading && state.error == null && state.companies.isNotEmpty() && state.cartProducts.isNotEmpty() && isOnline
            imgEmptyCart.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isEmpty() && isOnline
            txtTotal.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isNotEmpty() && isOnline
            txtTotalPrice.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isNotEmpty() && isOnline
            totalBackground.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isNotEmpty() && isOnline


            txtEmptyCart.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isEmpty()

            progressBar.isVisible = state.isLoading

            txtError.isVisible = if (state.isOnline == null) false else !isOnline
            imgNoInternetConnection.isVisible = if (state.isOnline == null) false else !isOnline

            txtTotalPrice.text = state.totalPrice.toString()
        }
        if (isOnline && state.cartProducts.isEmpty() && state.companies.isEmpty()) {
            viewModel.updateCachedData()
            viewModel.getCompanies()
        }
        cartProductAdapter.submitList(state.cartProducts)
        companyListAdapter.submitList(state.companies)

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