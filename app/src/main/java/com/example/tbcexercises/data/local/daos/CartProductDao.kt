package com.example.tbcexercises.data.local.daos

import androidx.room.*
import com.example.tbcexercises.data.local.entity.CartProductEntity
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface CartProductDao {


    @Query("SELECT * FROM cart_product_entity")
    fun getAllCartProducts(): Flow<List<CartProductEntity>>

    @Query("SELECT productId FROM cart_product_entity")
    fun getAllCartProductsIds(): Flow<List<Int>>


    @Query("UPDATE cart_product_entity SET productQuantity = productQuantity + 1 WHERE productId = :productId")
    suspend fun incrementCartProductQuantity(productId: Int)


    @Query("UPDATE cart_product_entity SET productQuantity = productQuantity + 1 WHERE productId = :productId")
    suspend fun decrementCartProductQuantity(productId: Int)


    @Query("DELETE FROM cart_product_entity WHERE productId = :productId")
    suspend fun deleteCartProductById(productId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(product: CartProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProducts(product: List<CartProductEntity>)

}
