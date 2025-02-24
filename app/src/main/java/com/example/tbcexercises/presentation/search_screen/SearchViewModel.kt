package com.example.tbcexercises.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.tbcexercises.domain.model.SearchProduct
import com.example.tbcexercises.presentation.mappers.local_to_presentation.toSearchProduct
import com.example.tbcexercises.domain.repository.product.FavouriteProductRepository
import com.example.tbcexercises.domain.repository.product.SearchProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductRepository: SearchProductRepository,
    private val favouriteProductRepository: FavouriteProductRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val searchQuery: StateFlow<String> = _searchQuery

    @OptIn(ExperimentalCoroutinesApi::class)
    private val baseSearchResults = searchQuery
        .filter { it.length >= 3 }
        .flatMapLatest { query ->
            searchProductRepository.getSearchedProductsPager(query)
                .map { pagingData -> pagingData.map { it.toSearchProduct() } }
        }
        .cachedIn(viewModelScope)

    private val favouriteIdsFlow = favouriteProductRepository
        .getAllFavouriteProductIds()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val searchResultsFlow = combine(
        baseSearchResults,
        favouriteIdsFlow
    ) { pagingData, favouriteIds ->
        pagingData.map { product ->
            product.copy(
                isFavourite = product.productId in favouriteIds
            )
        }
    }.cachedIn(viewModelScope)

    fun updateQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun setFavourite(product: SearchProduct) {
        viewModelScope.launch {
            val isFavourite = favouriteProductRepository.getAllFavouriteProductIds()
                .first()
                .contains(product.productId)

            if (isFavourite) {
                favouriteProductRepository.deleteFavouriteProduct(
                    product.toFavouriteProduct()
                )
            } else {
                favouriteProductRepository.insertFavouriteProduct(
                    product.toFavouriteProduct()
                )
            }
        }
    }
}
