package com.example.tbcexercises.presentation.detail_screen.company_prices_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemCompanyPricesBinding
import com.example.tbcexercises.domain.model.DetailProduct

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

            }
            Glide.with(binding.imgCompanyLogo.context)
                .load(companyPrices.companyImgUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_error)
                .into(binding.imgCompanyLogo)

        }
    }

}