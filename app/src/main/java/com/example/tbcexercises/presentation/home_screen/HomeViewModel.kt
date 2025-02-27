package com.example.tbcexercises.presentation.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.tbcexercises.data.mappers.toFavouriteProduct
import com.example.tbcexercises.data.mappers.toHomeProduct
import com.example.tbcexercises.domain.model.Category
import com.example.tbcexercises.domain.model.HomeProduct
import com.example.tbcexercises.domain.repository.category.CategoryRepository
import com.example.tbcexercises.domain.repository.product.FavouriteProductRepository
import com.example.tbcexercises.domain.repository.product.HomeProductRepository
import com.example.tbcexercises.utils.network_helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: HomeProductRepository,
    private val favouriteProductRepository: FavouriteProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    init {
        getCategories()
    }

    private val _searchQuery = MutableStateFlow<String?>(null)
    private val _category = MutableStateFlow<String?>(null)


    private val _categories =
        MutableStateFlow<Resource<List<Category>>?>(null)
    val categories: StateFlow<Resource<List<Category>>?> = _categories


    private val favouriteIdsFlow = favouriteProductRepository
        .getAllFavouriteProductIds()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val productFlow = combine(_searchQuery, _category, favouriteIdsFlow) { search, category, _ ->
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { productRepository.getProductsPagerSource(category, search) }
        ).flow.map { pagingData ->
            pagingData.map { product ->
                product.toHomeProduct().copy(
                    isFavourite = product.productId in favouriteIdsFlow.value
                )
            }
        }
    }.flatMapLatest { it }
        .cachedIn(viewModelScope)

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getCategories().collectLatest {
                _categories.value = it
            }
        }
    }


    fun setFavouriteStrategy(product: HomeProduct) {
        viewModelScope.launch {
            val isFavourite = favouriteIdsFlow.value.contains(product.productId)

            if (isFavourite) {
                favouriteProductRepository.deleteFavouriteProduct(
                    product.toFavouriteProductEntity().toFavouriteProduct()
                )
            } else {
                favouriteProductRepository.insertFavouriteProduct(
                    product.toFavouriteProductEntity().toFavouriteProduct()
                )
            }
        }
    }

    fun updateSearchQuery(query: String?) {
        _searchQuery.value = query
    }

    fun updateCategory(category: String?) {

        if (_category.value != category) {

            _category.value = category
            _categories.value?.let { resource ->
                if (resource is Resource.Success) {
                    val updatedCategories = resource.data.map { cat ->
                        cat.copy(isClicked = (cat.title == category))
                    }
                    _categories.value = Resource.Success(updatedCategories)
                }
            }
        } else {
            _category.value = null
            _categories.value?.let { resource ->
                if (resource is Resource.Success) {
                    val updatedCategories = resource.data.map { cat ->
                        cat.copy(isClicked = false)
                    }
                    _categories.value = Resource.Success(updatedCategories)
                }
            }
        }
    }
}
