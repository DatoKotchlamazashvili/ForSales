package com.example.tbcexercises.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.tbcexercises.data.mappers.local_to_presentation.toProduct
import com.example.tbcexercises.data.mappers.local_to_presentation.toProductFavouriteEntity
import com.example.tbcexercises.domain.model.HomeProduct
import com.example.tbcexercises.domain.repository.product.FavouriteProductRepository
import com.example.tbcexercises.domain.repository.product.HomeProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    productRepository: HomeProductRepository,
    private val favouriteProductRepository: FavouriteProductRepository
) :
    ViewModel() {
    private val favouriteIdsFlow = favouriteProductRepository
        .getAllFavouriteProductIds()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val basePagingFlow = productRepository.getProductsPager().cachedIn(viewModelScope)
    val productFlow = combine(
        basePagingFlow,
        favouriteIdsFlow
    ) { pagingData, favouriteIds ->
        pagingData.map { product ->
            product.toProduct().copy(
                isFavourite = product.productId in favouriteIds
            )
        }
    }.cachedIn(viewModelScope)


    fun setFavouriteStrategy(product: HomeProduct) {
        viewModelScope.launch {
            val isFavourite = favouriteProductRepository.getAllFavouriteProductIds()
                .first()
                .contains(product.productId)

            if (isFavourite) {
                favouriteProductRepository.deleteFavouriteProduct(
                    product.toFavouriteProductEntity().toProductFavouriteEntity()
                )
            } else {
                favouriteProductRepository.insertFavouriteProduct(
                    product.toFavouriteProductEntity().toProductFavouriteEntity()
                )
            }
        }
    }
}