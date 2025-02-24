package com.example.tbcexercises.presentation.favourite_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.model.FavouriteProduct
import com.example.tbcexercises.domain.repository.product.FavouriteProductRepository
import com.example.tbcexercises.utils.network_helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val favouriteProductRepository: FavouriteProductRepository) :
    ViewModel() {

    private val _favouriteProducts =
        MutableStateFlow<Resource<List<FavouriteProduct>>?>(null)
    val favouriteProducts: StateFlow<Resource<List<FavouriteProduct>>?> = _favouriteProducts

    init {
        getFavouriteProducts()
    }

    private fun getFavouriteProducts() {
        viewModelScope.launch {
            favouriteProductRepository.getAllFavouriteProducts().collectLatest { result ->
                _favouriteProducts.value = result
            }
        }
    }

    fun deleteFavouriteProduct(favouriteProduct: FavouriteProduct) {
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