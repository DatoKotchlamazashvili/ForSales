package com.example.tbcexercises.presentation.home_screen.category_list_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.home.Category

object CategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}