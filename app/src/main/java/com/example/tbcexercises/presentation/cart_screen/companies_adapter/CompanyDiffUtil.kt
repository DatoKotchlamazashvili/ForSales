package com.example.tbcexercises.presentation.cart_screen.companies_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.Company

object CompanyDiffUtil : DiffUtil.ItemCallback<Company>() {
    override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
        return oldItem == newItem
    }
}