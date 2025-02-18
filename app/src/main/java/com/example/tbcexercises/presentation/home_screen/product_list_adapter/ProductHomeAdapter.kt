package com.example.tbcexercises.presentation.home_screen.product_list_adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemProductHomeBinding
import com.example.tbcexercises.domain.model.ProductHome
import com.example.tbcexercises.presentation.home_screen.company_list_adapter.CompanyListAdapter

class ProductHomeAdapter :
    PagingDataAdapter<ProductHome, ProductHomeAdapter.ProductHomeViewHolder>(ProductHomeDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHomeViewHolder {
        val binding =
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHomeViewHolder, position: Int) {
        holder.onBind()
    }

    inner class ProductHomeViewHolder(private val binding: ItemProductHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {

            val product = getItem(bindingAdapterPosition)!!
            Glide.with(binding.imgProduct.context)
                .load(product.productImgUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_error)
                .into(binding.imgProduct)


            binding.apply {
                txtProductName.text = product.productName
                txtPrice.text = "₾" + product.productPrice.toString()
                txtCategory.text = product.productCategory

            }
            Log.d("product",product.toString())

            val companyAdapter = CompanyListAdapter()
            companyAdapter.submitList(product.company)
        }
    }
}