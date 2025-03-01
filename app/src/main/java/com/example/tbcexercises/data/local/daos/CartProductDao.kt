package com.example.tbcexercises.data.local.daos

import androidx.room.*
import com.example.tbcexercises.data.local.entity.CartProductEntity
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface CartProductDao {


    @Query("SELECT * FROM cart_product_entity where company = :companyName")
    fun getAllCartProducts(companyName: String): Flow<List<CartProductEntity>>

    @Query("SELECT productId FROM cart_product_entity")
    fun getAllCartProductsIds(): Flow<List<Int>>


    @Query("UPDATE cart_product_entity SET productQuantity = productQuantity + 1 WHERE productId = :productId")
    suspend fun incrementCartProductQuantity(productId: Int)


    @Query("UPDATE cart_product_entity SET productQuantity = productQuantity - 1 WHERE productId = :productId")
    suspend fun decrementCartProductQuantity(productId: Int)


    @Query("DELETE FROM cart_product_entity WHERE productId = :productId")
    suspend fun deleteCartProductById(productId: Int)

    @Upsert
    suspend fun upsertCartProduct(product: CartProductEntity)

    @Upsert
    suspend fun upsertCartProducts(product: List<CartProductEntity>)

}
