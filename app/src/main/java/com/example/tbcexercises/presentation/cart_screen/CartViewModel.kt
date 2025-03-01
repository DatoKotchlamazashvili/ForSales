package com.example.tbcexercises.presentation.cart_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.model.CartProduct
import com.example.tbcexercises.domain.repository.company.CompanyRepository
import com.example.tbcexercises.domain.repository.product.CartProductRepository
import com.example.tbcexercises.utils.network_helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartProductRepository: CartProductRepository,
    private val companyRepository: CompanyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartScreenUiState())
    val uiState: StateFlow<CartScreenUiState> = _uiState

    private var currentProductsJob: Job? = null

    init {
        updateCachedData()
        getCompanies()
    }

    private fun getCompanies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            companyRepository.getCompanies().collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val companies = resource.data
                        val selectedCompany = companies.find { it.isClicked } ?: companies.firstOrNull()

                        _uiState.update {
                            it.copy(
                                companies = companies,
                                selectedCompanyName = selectedCompany?.company,
                                isLoading = false
                            )
                        }
                        selectedCompany?.let { getCartProducts(selectedCompany.company) }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                error = resource.message,
                                isLoading = false
                            )
                        }
                    }
                    Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun updateCachedData() {
        viewModelScope.launch(Dispatchers.IO) {
            cartProductRepository.saveCartProducts()
        }
    }

    private fun getCartProducts(companyName: String? = null) {
        currentProductsJob?.cancel()

        val targetCompany = companyName ?: _uiState.value.selectedCompanyName
        if (targetCompany == null) return

        Log.d("getCartProducts", "Getting products for: $targetCompany")

        currentProductsJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            cartProductRepository.getAllCartProducts(targetCompany)
                .collect { result ->
                    val currentSelectedCompany = _uiState.value.selectedCompanyName
                    if (currentSelectedCompany == targetCompany) {
                        when (result) {
                            is Resource.Success -> {
                                val products = result.data
                                Log.d("products for $targetCompany", products.toString())

                                _uiState.update { state ->
                                    state.copy(
                                        cartProducts = products,
                                        totalPrice = products.sumOf { it.totalPrice }.toFloat(),
                                        isLoading = false
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _uiState.update {
                                    it.copy(
                                        error = result.message,
                                        isLoading = false
                                    )
                                }
                            }
                            Resource.Loading -> {
                                _uiState.update { it.copy(isLoading = true) }
                            }
                        }
                    } else {
                        Log.d("Ignored update", "Ignoring update for $targetCompany, current is $currentSelectedCompany")
                    }
                }
        }
    }

    fun deleteProduct(cartProduct: CartProduct) {
        viewModelScope.launch {
            cartProductRepository.deleteCartProduct(cartProduct)
        }
    }

    fun incrementQuantity(cartProduct: CartProduct) {
        viewModelScope.launch {
            cartProductRepository.incrementCartProductQuantity(cartProduct)
        }
    }

    fun decrementQuantity(cartProduct: CartProduct) {
        if (cartProduct.quantity > 1) {
            viewModelScope.launch {
                cartProductRepository.decrementCartProductQuantity(cartProduct)
            }
        }
    }

    fun selectCompany(companyName: String) {
        // Update UI state immediately
        _uiState.update { currentState ->
            val updatedCompanies = currentState.companies.map { company ->
                company.copy(isClicked = company.company == companyName)
            }
            currentState.copy(
                companies = updatedCompanies,
                selectedCompanyName = companyName
            )
        }

        getCartProducts(companyName)
    }
}


