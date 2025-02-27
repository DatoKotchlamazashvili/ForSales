package com.example.tbcexercises.data.repository.cart

import com.example.tbcexercises.data.local.daos.CartProductDao
import com.example.tbcexercises.data.mappers.toCartProduct
import com.example.tbcexercises.data.mappers.toCartProductEntity
import com.example.tbcexercises.data.remote.response.CartProductResponse
import com.example.tbcexercises.data.remote.service.CartProductService
import com.example.tbcexercises.domain.model.CartProduct
import com.example.tbcexercises.domain.repository.product.CartProductRepository
import com.example.tbcexercises.utils.network_helper.Resource
import com.example.tbcexercises.utils.network_helper.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartProductRepositoryImpl @Inject constructor(
    private val cartProductDao: CartProductDao,
    private val cartProductService: CartProductService
) : CartProductRepository {
    override suspend fun insertCartProduct(cartProduct: CartProduct) {
        cartProductDao.insertCartProduct(cartProduct.toCartProductEntity())
    }

    override suspend fun insertCartProducts(cartProduct: List<CartProduct>) {
        cartProductDao.insertCartProducts(cartProduct.map { it.toCartProductEntity() })
    }

    override suspend fun deleteCartProduct(cartProduct: CartProduct) {
        cartProductDao.deleteCartProductById(cartProduct.productId)
    }

    override fun getAllCartProducts(): Flow<Resource<List<CartProduct>>> = flow {
        emit(Resource.Loading)
        try {
            cartProductDao.getAllCartProducts().collect { products ->
                emit(Resource.Success(products.map { it.toCartProduct() }))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }


    override fun getAllCartProductIds(): Flow<List<Int>> {
        return cartProductDao.getAllCartProductsIds()
    }

    override fun getCartProductsFromServer(ids: String): Flow<Resource<List<CartProductResponse>>> {
        return handleNetworkRequest { cartProductService.getCartProductsByIds(ids) }
    }

    override suspend fun saveCartProducts() {
        val existingIds = getAllCartProductIds().first()

        if (existingIds.isNotEmpty()) {
            getCartProductsFromServer(existingIds.joinToString(",") { it.toString() }).collect { resource ->
                when (resource) {
                    is Resource.Success -> {

                        insertCartProducts(resource.data.map {
                            it.toCartProductEntity().toCartProduct()
                        })
                    }

                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }
}