package com.example.tbcexercises.data.repository.product


import com.example.tbcexercises.data.local.daos.CartProductDao
import com.example.tbcexercises.data.mappers.cart.toDomainCartProduct
import com.example.tbcexercises.data.remote.service.CartProductService
import com.example.tbcexercises.domain.model.cart.CartProduct
import com.example.tbcexercises.domain.repository.product.CartProductRepository
import com.example.tbcexercises.presentation.mappers.toCartProductEntity
import com.example.tbcexercises.utils.network_helper.Resource
import com.example.tbcexercises.utils.network_helper.handleNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartProductRepositoryImpl @Inject constructor(
    private val cartProductDao: CartProductDao,
    private val cartProductService: CartProductService
) : CartProductRepository {
    override suspend fun upsertCartProduct(cartProduct: CartProduct) {
        cartProductDao.upsertCartProduct(cartProduct.toCartProductEntity())
    }

    override suspend fun upsertCartProducts(cartProduct: List<CartProduct>) {
        cartProductDao.upsertCartProducts(cartProduct.map { it.toCartProductEntity() })
    }

    override suspend fun deleteCartProduct(cartProduct: CartProduct) {
        cartProductDao.deleteCartProductById(cartProduct.productId)
    }

    override suspend fun incrementCartProductQuantity(cartProduct: CartProduct) {
        cartProductDao.incrementCartProductQuantity(cartProduct.productId)
    }

    override suspend fun decrementCartProductQuantity(cartProduct: CartProduct) {
        cartProductDao.decrementCartProductQuantity(cartProduct.productId)
    }

    override fun getAllCartProducts(company: String): Flow<Resource<List<CartProduct>>> = flow {
        emit(Resource.Loading)
        try {
            cartProductDao.getAllCartProducts(company).collect { products ->
                emit(Resource.Success(products.map { it.toDomainCartProduct() }))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }


    override fun getAllCartProductIds(): Flow<List<Int>> {
        return cartProductDao.getAllCartProductsIds()
    }

    override fun getCartProductsFromServer(ids: String): Flow<Resource<List<CartProduct>>> {
        return handleNetworkRequest { cartProductService.getCartProductsByIds(ids) }.map { result ->
            when (result) {
                is Resource.Error -> Resource.Error(result.message)
                Resource.Loading -> Resource.Loading
                is Resource.Success -> Resource.Success(result.data.map { it.toDomainCartProduct() })
            }
        }
    }

    override suspend fun saveCartProducts() {
        val existingIds = getAllCartProductIds().first().distinct()

        if (existingIds.isNotEmpty()) {
            getCartProductsFromServer(existingIds.joinToString(",") { it.toString() }).collect { resource ->
                when (resource) {
                    is Resource.Success -> {

                        upsertCartProducts(resource.data.map {
                            it.toCartProductEntity().toDomainCartProduct()
                        })
                    }

                    else -> Unit
                }
            }
        }
    }
}