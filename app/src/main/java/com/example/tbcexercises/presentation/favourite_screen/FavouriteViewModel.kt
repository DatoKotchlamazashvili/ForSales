package com.example.tbcexercises.presentation.favourite_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.data.connectivity.ConnectivityObserver
import com.example.tbcexercises.domain.model.favourite.FavouriteProduct
import com.example.tbcexercises.domain.repository.product.CartProductRepository
import com.example.tbcexercises.domain.repository.product.FavouriteProductRepository
import com.example.tbcexercises.presentation.mappers.toCartProduct
import com.example.tbcexercises.utils.network_helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouriteProductRepository: FavouriteProductRepository,
    connectivityObserver: ConnectivityObserver,
    private val cartProductRepository: CartProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteScreenUiState())
    val uiState: StateFlow<FavouriteScreenUiState> = _uiState

    private val isConnected = connectivityObserver.isConnected

    private val cartProductIdsFlow = cartProductRepository.getAllCartProductIds()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    init {
        updateFavouriteProducts()
        observeCartAndFavourites()
    }

    private fun updateFavouriteProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isConnected.first()) {
                favouriteProductRepository.saveFavouriteProducts()
            }
        }
    }

    private fun observeCartAndFavourites() {
        viewModelScope.launch {
            combine(
                favouriteProductRepository.getAllFavouriteProducts()
                    .distinctUntilChanged(),
                cartProductIdsFlow
            ) { favouriteProductsResource, cartProductIds ->
                when (favouriteProductsResource) {
                    is Resource.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = favouriteProductsResource.message
                            )
                        }
                    }

                    Resource.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = true,
                                error = null
                            )
                        }
                    }

                    is Resource.Success -> {
                        val updatedProducts = favouriteProductsResource.data.map { product ->
                            product.copy(isAddedToCart = product.productId in cartProductIds)
                        }
                        Log.d("products", "$updatedProducts")
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                favouriteProducts = updatedProducts,
                                error = null
                            )
                        }
                    }
                }
            }.collectLatest { }
        }
    }


    fun addToCart(favouriteProduct: FavouriteProduct) {
        if (!favouriteProduct.isAddedToCart) {
            viewModelScope.launch(Dispatchers.IO) {
                cartProductRepository.upsertCartProduct(favouriteProduct.toCartProduct())
            }
        }
    }

    fun deleteFavouriteProduct(favouriteProduct: FavouriteProduct) {
        Log.d("deleteCalled", "deleteCalled")
        viewModelScope.launch {
            favouriteProductRepository.deleteFavouriteProduct(favouriteProduct)
        }
    }

    fun insertFavouriteProduct(favouriteProduct: FavouriteProduct) {
        viewModelScope.launch {
            favouriteProductRepository.insertFavouriteProduct(favouriteProduct)
        }
    }
}