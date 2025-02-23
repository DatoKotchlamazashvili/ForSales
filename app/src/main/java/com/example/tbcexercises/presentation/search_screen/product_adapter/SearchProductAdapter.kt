package com.example.tbcexercises.presentation.search_screen.product_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemProductSearchBinding
import com.example.tbcexercises.domain.model.SearchProduct
import com.example.tbcexercises.presentation.search_screen.company_adapter.SearchCompanyAdapter
import com.example.tbcexercises.utils.GlideImageLoader

class SearchProductAdapter(val onFavouriteClick: (Int) -> Unit) :
    PagingDataAdapter<SearchProduct, SearchProductAdapter.SearchProductViewHolder>(
        SearchProductDiffUtil
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchProductAdapter.SearchProductViewHolder {
        val binding =
            ItemProductSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SearchProductAdapter.SearchProductViewHolder,
        position: Int
    ) {
        holder.onBind()
    }

    inner class SearchProductViewHolder(val binding: ItemProductSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val product = getItem(bindingAdapterPosition)

            val companyAdapter = SearchCompanyAdapter()
            product?.let {
                binding.apply {
                    txtProductName.text = it.productName
                    txtProductCategory.text = it.productCategory
                    GlideImageLoader.loadImage(imgProduct, it.productImgUrl)

                    rvCompanyPrices.adapter = companyAdapter
                    rvCompanyPrices.layoutManager = LinearLayoutManager(root.context)
                }

                companyAdapter.submitList(product.company.toList())

            }


        }

    }
}