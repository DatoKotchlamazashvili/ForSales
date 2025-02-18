package com.example.tbcexercises.presentation.home_screen.company_list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemCompanyBinding
import com.example.tbcexercises.domain.model.Company

class CompanyListAdapter :
    ListAdapter<Company, CompanyListAdapter.CompanyListViewHolder>(CompanyDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyListViewHolder {
        val binding = ItemCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyListViewHolder, position: Int) {
        holder.onBind()
    }

    inner class CompanyListViewHolder(private val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val company = getItem(bindingAdapterPosition)

            Glide.with(binding.imgCompanyLogo)
                .load(company.companyImgUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_error)
                .into(binding.imgCompanyLogo)


        }
    }

}