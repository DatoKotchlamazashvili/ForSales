package com.example.tbcexercises.presentation.favourite_screen


import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentFavouriteBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.favourite_screen.favorute_product_adapter.FavouriteProductAdapter
import com.example.tbcexercises.utils.network_helper.Resource
import com.example.tbcexercises.utils.extension.collectLastState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouriteFragment :
    BaseFragment<FragmentFavouriteBinding>(FragmentFavouriteBinding::inflate) {
    private val viewModel: FavouriteViewModel by viewModels()

    private val favouriteProductAdapter by lazy {
        FavouriteProductAdapter(onClickToAddCart = {
            viewModel.addToCart(it)
        })
    }

    override fun start() {
        setUpAdapter()
        collectLastState(viewModel.uiState) { state ->
            updateUI(state)
        }
    }

    private fun updateUI(state: FavouriteScreenUiState) {
        binding.apply {
            Log.d("favouriteState", state.toString())
            progressBar.isVisible = state.isLoading
            rvProducts.isVisible =
                !state.isLoading && state.error == null && state.favouriteProducts.isNotEmpty()
            txtError.isVisible = state.error != null

            imgEmptyBox.isVisible =
                !state.isLoading && state.error == null && state.favouriteProducts.isEmpty()
            txtEmptyBox.isVisible =
                !state.isLoading && state.error == null && state.favouriteProducts.isEmpty()
            state.error?.let {
                txtError.text = state.error
            }

            if (!state.isLoading && state.error == null) {
                favouriteProductAdapter.submitList(state.favouriteProducts.toList())
            }
        }
    }

    private fun setUpAdapter() {
        binding.rvProducts.apply {
            adapter = favouriteProductAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val swipeToDelete = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.bindingAdapterPosition
                val item = favouriteProductAdapter.currentList[pos]
                viewModel.deleteFavouriteProduct(item)
                Snackbar.make(
                    requireView(),
                    getString(R.string.successfully_deleted_item),
                    Snackbar.LENGTH_LONG
                ).apply {
                    setAction(getString(R.string.undo)) {
                        viewModel.insertFavouriteProduct(item)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(swipeToDelete).attachToRecyclerView(binding.rvProducts)
    }
}