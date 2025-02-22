package com.example.tbcexercises.presentation.home_screen.product_list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemProductHomeBinding
import com.example.tbcexercises.domain.model.ProductHome
import com.example.tbcexercises.presentation.home_screen.company_list_adapter.CompanyListAdapter
import com.example.tbcexercises.utils.GlideImageLoader
import com.example.tbcexercises.utils.extension.setTint

class ProductHomeAdapter(
    val onClick: (Int) -> Unit,
    val onFavouriteClick: (ProductHome) -> Unit,

    ) :
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
            //will have null pointer problem
            val product = getItem(bindingAdapterPosition)

            product?.let {

                GlideImageLoader.loadImage(binding.imgProduct, url = product.productImgUrl)
                val companyAdapter = CompanyListAdapter()


                binding.apply {
                    txtProductName.text = product.productName
                    txtPrice.text = root.context.getString(
                        R.string.money_format,
                        product.productPrice.toString()
                    )
                    txtCategory.text = product.productCategory

                    root.setOnClickListener {
                        onClick(product.productId)
                    }
                    rvCompanies.layoutManager =
                        LinearLayoutManager(
                            binding.root.context,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )

                    rvCompanies.adapter = companyAdapter

                    imgFavourite.setOnClickListener {
                        onFavouriteClick(product)
                    }
                    if (product.isFavourite) {
                        imgFavourite.setTint(R.color.red)
                    } else {
                        imgFavourite.setTint(R.color.gray)
                    }
                }
                companyAdapter.submitList(product.company)
            }
        }
    }
}