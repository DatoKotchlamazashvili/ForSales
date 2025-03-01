package com.example.tbcexercises.presentation.cart_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.model.CartProduct
import com.example.tbcexercises.domain.model.Company
import com.example.tbcexercises.domain.repository.company.CompanyRepository
import com.example.tbcexercises.domain.repository.product.CartProductRepository
import com.example.tbcexercises.utils.network_helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartProductRepository: CartProductRepository,
    private val companyRepository: CompanyRepository
) : ViewModel() {

    private val _cartProducts =
        MutableStateFlow<Resource<List<CartProduct>>?>(null)
    val cartProducts: StateFlow<Resource<List<CartProduct>>?> = _cartProducts

    private val _companies =
        MutableStateFlow<Resource<List<Company>>?>(null)
    val companies: StateFlow<Resource<List<Company>>?> = _companies

    init {
        updateCachedData()
        getCompanies()

        viewModelScope.launch {
            _companies.filterNotNull().collectLatest { resource ->
                if (resource is Resource.Success && resource.data.isNotEmpty()) {
                    val selectedCompany = resource.data.find { it.isClicked }?.company
                        ?: resource.data.first().company
                    getCartProducts(selectedCompany)
                }
            }
        }
    }

    fun getCompanies() {
        viewModelScope.launch {
            companyRepository.getCompanies().collectLatest {
                _companies.value = it

                if (it is Resource.Success && it.data.isNotEmpty()) {
                    val selectedCompany = it.data.find { company -> company.isClicked }
                    selectedCompany?.let { company ->
                        getCartProducts(company.company)
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

    fun getCartProducts(company: String? = null) {
        viewModelScope.launch {
            val selectedCompany = company ?: _companies.value?.let { resource ->
                if (resource is Resource.Success) {
                    val clickedCompany = resource.data.find { it.isClicked }
                    clickedCompany?.company
                } else null
            }

            selectedCompany?.let {
                cartProductRepository.getAllCartProducts(it).collectLatest { result ->
                    _cartProducts.value = result
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
    fun selectCompany(companyId: Int) {
        val currentCompanies = _companies.value
        if (currentCompanies is Resource.Success) {
            val updatedCompanies = currentCompanies.data.map { company ->
                company.copy(isClicked = company.id == companyId)
            }

            _companies.value = Resource.Success(updatedCompanies)

            val selectedCompany = updatedCompanies.find { it.isClicked }
            selectedCompany?.let {
                getCartProducts(it.company)
            }
        }
    }
}

