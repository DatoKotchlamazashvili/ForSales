package com.example.tbcexercises.presentation.search_screen.company_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemCompanyPricesBinding
import com.example.tbcexercises.domain.model.SearchCompany
import com.example.tbcexercises.utils.GlideImageLoader

class SearchCompanyAdapter :
    ListAdapter<SearchCompany, SearchCompanyAdapter.SearchCompanyViewHolder>(SearchCompanyDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCompanyViewHolder {
        val binding =
            ItemCompanyPricesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchCompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchCompanyViewHolder, position: Int) {
        holder.onBind()
    }


    inner class SearchCompanyViewHolder(val binding: ItemCompanyPricesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val company = getItem(bindingAdapterPosition)
            binding.apply {
                txtCompanyName.text = company.name
                txtCompanyPrice.text = company.productPrice

                GlideImageLoader.loadImage(imgCompanyLogo, company.companyImgUrl)
            }
        }
    }

}