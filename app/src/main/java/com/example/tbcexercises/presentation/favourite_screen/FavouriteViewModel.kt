package com.example.tbcexercises.presentation.favourite_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.model.ProductFavourite
import com.example.tbcexercises.domain.repository.FavouriteProductRepository
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
        MutableStateFlow<Resource<List<ProductFavourite>>?>(null)
    val favouriteProducts: StateFlow<Resource<List<ProductFavourite>>?> = _favouriteProducts

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

    fun deleteFavouriteProduct(productFavourite: ProductFavourite) {
        viewModelScope.launch {
            favouriteProductRepository.deleteFavouriteProduct(productFavourite.toProductEntity())
        }
    }

    fun insertFavouriteProduct(productFavourite: ProductFavourite) {
        viewModelScope.launch {
            favouriteProductRepository.insertFavouriteProduct(productFavourite.toProductEntity())
        }
    }
}