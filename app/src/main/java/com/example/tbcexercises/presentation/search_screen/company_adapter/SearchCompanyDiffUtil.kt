package com.example.tbcexercises.presentation.search_screen.company_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.search.SearchCompany

object SearchCompanyDiffUtil : DiffUtil.ItemCallback<SearchCompany>() {
    override fun areItemsTheSame(oldItem: SearchCompany, newItem: SearchCompany): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: SearchCompany, newItem: SearchCompany): Boolean {
        return oldItem == newItem
    }
}