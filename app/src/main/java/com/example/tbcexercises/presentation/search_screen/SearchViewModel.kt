package com.example.tbcexercises.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.tbcexercises.data.mappers.local_to_presentation.toSearchProduct
import com.example.tbcexercises.domain.repository.product.FavouriteProductRepository
import com.example.tbcexercises.domain.repository.product.SearchProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductRepository: SearchProductRepository,
    private val favouriteProductRepository: FavouriteProductRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val searchQuery: StateFlow<String> = _searchQuery

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults = searchQuery
        .filter { it.length >= 3 }
        .flatMapLatest { query ->
            searchProductRepository.getSearchedProductsPager(query)
                .map { data -> data.map { it.toSearchProduct() } }
        }
        .cachedIn(viewModelScope)

    fun updateQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }
}
