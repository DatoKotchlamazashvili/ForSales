package com.example.tbcexercises.presentation.home_screen.product_load_adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ProductLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ProductLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = ProductLoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: ProductLoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}