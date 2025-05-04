package com.example.tbcexercises.presentation.home_screen.company_list_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.home.HomeCompany


object CompanyDiffUtil : DiffUtil.ItemCallback<HomeCompany>() {
    override fun areItemsTheSame(oldItem: HomeCompany, newItem: HomeCompany): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: HomeCompany, newItem: HomeCompany): Boolean {
        return oldItem == newItem
    }
}