package com.example.tbcexercises.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.tbcexercises.data.mappers.home.toDomainHomeProduct
import com.example.tbcexercises.domain.model.home.HomeProduct
import com.example.tbcexercises.domain.repository.category.CategoryRepository
import com.example.tbcexercises.domain.repository.product.CartProductRepository
import com.example.tbcexercises.domain.repository.product.FavouriteProductRepository
import com.example.tbcexercises.domain.repository.product.HomeProductRepository
import com.example.tbcexercises.presentation.mappers.toCartProduct
import com.example.tbcexercises.presentation.mappers.toFavouriteProduct
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: HomeProductRepository,
    private val favouriteProductRepository: FavouriteProductRepository,
    private val categoryRepository: CategoryRepository,
    private val cartProductRepository: CartProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    init {
        getCategories()
    }

    private val favouriteIdsFlow = favouriteProductRepository
        .getAllFavouriteProductIds()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    private val cartProductIdsFlow = cartProductRepository.getAllCartProductIds()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val productFlow = combine(
        uiState,
        favouriteIdsFlow,
        cartProductIdsFlow
    ) { state, favouriteProducts, cartProducts ->
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                productRepository.getProductsPagerSource(
                    state.selectedCategory,
                    state.searchQuery
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { product ->
                product.toDomainHomeProduct().copy(
                    isFavourite = product.productId in favouriteProducts,
                    isAddedToCart = product.productId in cartProducts
                )
            }
        }
    }.flatMapLatest { it }
        .cachedIn(viewModelScope)

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getCategories().collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = null
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
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                categories = resource.data.toList(),
                                error = null
                            )
                        }
                    }
                }
            }
        }
    }

    fun setFavouriteStrategy(product: HomeProduct) {
        viewModelScope.launch {
            val isFavourite = favouriteIdsFlow.value.contains(product.productId)

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

    fun updateSearchQuery(query: String?) {
        _uiState.update { currentState ->
            currentState.copy(searchQuery = query)
        }
    }

    fun updateCategory(category: String?) {
        val currentCategory = _uiState.value.selectedCategory

        if (currentCategory != category) {
            _uiState.update { currentState ->
                currentState.copy(
                    selectedCategory = category,
                    categories = currentState.categories.map { cat ->
                        cat.copy(isClicked = (cat.title == category))
                    }
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    selectedCategory = null,
                    categories = currentState.categories.map { cat ->
                        cat.copy(isClicked = false)
                    }
                )
            }
        }
    }

    fun insertCartProduct(homeProduct: HomeProduct) {
        if (!homeProduct.isAddedToCart) {
            viewModelScope.launch(Dispatchers.IO) {
                cartProductRepository.upsertCartProduct(homeProduct.toCartProduct())
            }
        }
    }
}