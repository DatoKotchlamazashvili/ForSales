package com.example.tbcexercises.presentation.home_screen.category_list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemCategoryBinding
import com.example.tbcexercises.domain.model.home.Category
import com.example.tbcexercises.utils.extension.loadImg

class CategoryListAdapter(val onClick: (String) -> Unit) :
    ListAdapter<Category, CategoryListAdapter.CategoryViewHolder>(CategoryDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind()
    }

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val category = getItem(bindingAdapterPosition)
            binding.apply {
                imgCategory.loadImg(category.categoryImgUrl)

                txtCategory.text = category.title


                if (category.isClicked) {
                    root.background = ContextCompat.getDrawable(
                        root.context,
                        R.drawable.item_category_background_selected
                    )
                } else {
                    root.background = ContextCompat.getDrawable(
                        root.context,
                        R.drawable.item_category_background_unselected
                    )
                }

                root.setOnClickListener {
                    onClick(category.title)
                }
            }
        }
    }
}