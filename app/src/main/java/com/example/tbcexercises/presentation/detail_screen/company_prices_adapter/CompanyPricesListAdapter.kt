package com.example.tbcexercises.presentation.detail_screen.company_prices_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemCompanyPricesBinding
import com.example.tbcexercises.domain.model.detail.DetailProduct
import com.example.tbcexercises.utils.extension.loadImg

class CompanyPricesListAdapter :
    ListAdapter<DetailProduct, CompanyPricesListAdapter.CompanyPricesViewHolder>(
        CompanyPricesDiffUtil
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyPricesViewHolder {
        val binding =
            ItemCompanyPricesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyPricesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyPricesViewHolder, position: Int) {
        holder.onBind()
    }

    inner class CompanyPricesViewHolder(private val binding: ItemCompanyPricesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val companyPrices = getItem(bindingAdapterPosition)
            binding.apply {
                txtCompanyPrice.text = companyPrices.productPrice.toString()
                txtCompanyName.text = companyPrices.company

                imgCompanyLogo.loadImg(companyPrices.companyImgUrl)
            }
        }
    }

}