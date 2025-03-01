package com.example.tbcexercises.presentation.cart_screen.cart_product_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemCartBinding
import com.example.tbcexercises.domain.model.CartProduct
import com.example.tbcexercises.utils.extension.loadImg

class CartProductListAdapter(
    val onRemoveClick: (CartProduct) -> Unit,
    val onDecreaseClick: (CartProduct) -> Unit,
    val onIncreaseClick: (CartProduct) -> Unit,
) :
    ListAdapter<CartProduct, CartProductListAdapter.CartProductViewHolder>(CartProductDIffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.onBind()
    }

    inner class CartProductViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val cartProduct = getItem(bindingAdapterPosition)

            binding.apply {
                imgProduct.loadImg(cartProduct.productImgUrl)

                txtProductName.text = cartProduct.productName
                textQuantity.text = cartProduct.quantity.toString()
                txtPrice.text = cartProduct.productPrice.toString()

                btnRemove.setOnClickListener {
                    onRemoveClick(cartProduct)
                }

                btnDecrease.setOnClickListener {
                    onDecreaseClick(cartProduct)
                }

                btnIncrease.setOnClickListener {
                    onIncreaseClick(cartProduct)
                }
            }
        }
    }
}