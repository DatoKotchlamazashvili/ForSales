package com.example.tbcexercises.presentation.home_screen.company_list_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.Company


object CompanyDiffUtil : DiffUtil.ItemCallback<Company>() {
    override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
        return oldItem == newItem
    }
}