package com.example.tbcexercises.presentation.home_screen.product_load_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemLoadStateBinding


class ProductLoadStateViewHolder(
    parent: ViewGroup,
    val retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_load_state, parent, false)
) {
    private val binding = ItemLoadStateBinding.bind(itemView)


    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.txtErrorMsg.text = loadState.error.localizedMessage
        }

        binding.apply {
            progressBar.isVisible = loadState is LoadState.Loading

            btnRetry.isVisible = loadState is LoadState.Error
            btnRetry.setOnClickListener {
                retry()
            }
            txtErrorMsg.isVisible = loadState is LoadState.Error
        }
    }
}