package com.example.tbcexercises.presentation.search_screen.product_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemProductSearchBinding
import com.example.tbcexercises.domain.model.SearchProduct
import com.example.tbcexercises.presentation.search_screen.company_adapter.SearchCompanyAdapter
import com.example.tbcexercises.utils.GlideImageLoader
import com.example.tbcexercises.utils.extension.setTint

class SearchProductAdapter(val onFavouriteClick: (SearchProduct) -> Unit) :
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
                    GlideImageLoader.loadImage(imgProduct, it.productImgUrl)

                    rvCompanyPrices.adapter = companyAdapter
                    rvCompanyPrices.layoutManager = LinearLayoutManager(root.context)

                    rvCompanyPrices.setOnTouchListener { _, event ->
                        binding.root.parent.requestDisallowInterceptTouchEvent(true)
                        false
                    }


                    imgFavourite.setOnClickListener {
                        onFavouriteClick(product)
                    }
                    if (product.isFavourite) {
                        imgFavourite.setTint(R.color.red)
                    } else {
                        imgFavourite.setTint(R.color.gray)
                    }
                }
                companyAdapter.submitList(product.company.toList())
            }
        }
    }
}