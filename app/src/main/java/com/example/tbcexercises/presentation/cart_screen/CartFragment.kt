package com.example.tbcexercises.presentation.cart_screen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentCartBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.cart_screen.cart_product_adapter.CartProductListAdapter
import com.example.tbcexercises.presentation.cart_screen.companies_adapter.CompanyListAdapter
import com.example.tbcexercises.utils.extension.collectLastState
import com.example.tbcexercises.utils.extension.toast
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
    }


    private fun updateUI(state: CartScreenUiState) {
        binding.apply {
            rvCartProducts.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isNotEmpty()
            rvCompanies.isVisible =
                !state.isLoading && state.error == null && state.companies.isNotEmpty() && state.cartProducts.isNotEmpty()
            imgEmptyCart.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isEmpty()
            txtTotal.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isNotEmpty()
            txtTotalPrice.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isNotEmpty()
            totalBackground.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isNotEmpty()


            txtEmptyCart.isVisible =
                !state.isLoading && state.error == null && state.cartProducts.isEmpty()

            progressBar.isVisible = state.isLoading

            txtTotalPrice.text = state.totalPrice.toString()
        }
        cartProductAdapter.submitList(state.cartProducts)
        companyListAdapter.submitList(state.companies)
        state.error?.let {
            toast(it)
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